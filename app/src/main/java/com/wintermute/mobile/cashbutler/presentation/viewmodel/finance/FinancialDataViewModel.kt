package com.wintermute.mobile.cashbutler.presentation.viewmodel.finance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinanceDataEntity
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Transaction
import com.wintermute.mobile.cashbutler.data.repository.CashFlowRepository
import com.wintermute.mobile.cashbutler.domain.finance.FinancialCategories
import com.wintermute.mobile.cashbutler.presentation.intent.FinancialActionIntent
import com.wintermute.mobile.cashbutler.presentation.viewmodel.BaseViewModel
import com.wintermute.mobile.cashbutler.presentation.viewmodel.state.finance.FinancialState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Handles managing the financial data by end user.
 *
 * @author k.kosinski
 */
@HiltViewModel
class FinancialDataViewModel @Inject constructor(
    private val repository: CashFlowRepository
) : ViewModel(), BaseViewModel<FinancialActionIntent> {

    private val _state = MutableStateFlow<FinancialState>(FinancialState.Uninitialized)
    val state: MutableStateFlow<FinancialState> = _state

    override fun processIntent(intent: FinancialActionIntent) {
        when (intent) {
            is FinancialActionIntent.InitState -> initState(intent.category)
            is FinancialActionIntent.AddItem -> {
                if (intent.item.id > 0) {
                    updateItem(intent.item)
                } else {
                    addItem(intent.item)
                }
            }

            is FinancialActionIntent.RemoveItem -> removeItem(intent.item)

            is FinancialActionIntent.UpdateItem -> updateItem(intent.item)

            FinancialActionIntent.DestroyState -> _state.value = FinancialState.Uninitialized
        }
    }

    private fun initState(targetCategory: FinancialCategories) {
        when (targetCategory) {
            FinancialCategories.CASH_FLOW -> {
                viewModelScope.launch(Dispatchers.IO) {
                    repository.getCashFlowData().collect {
                        val categories =
                            it.map { categoryWithAccounts -> categoryWithAccounts.category }

                        val accounts = it.flatMap { categoryWithAccounts ->
                            categoryWithAccounts.accounts.map { flattenCategoryWithAccounts ->
                                flattenCategoryWithAccounts.account
                            }
                        }.groupBy { account -> account.categoryId }

                        val transactions =
                            it.flatMap { categoriesWithAccounts ->
                                categoriesWithAccounts.accounts
                                    .flatMap { accountsWithTransactions -> accountsWithTransactions.transactions }
                            }.groupBy { transaction -> transaction.accountId }

                        _state.value = FinancialState.Initialized(
                            rootCategory = FinancialCategories.CASH_FLOW,
                            categories = categories,
                            accounts = accounts,
                            transactions = transactions
                        )
                    }
                }
            }

            FinancialCategories.SAVINGS -> {}
            FinancialCategories.EXPENSES -> {}
        }
    }


    private fun addItem(item: FinanceDataEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            when (item) {
                is FinancialCategory -> repository.insertCategory(item)
                is Transaction -> repository.insertTransaction(item)
                else -> repository.addItem(item)
            }
        }
    }

    private fun updateItem(item: FinanceDataEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            when (item) {
                is Transaction -> repository.updateTransaction(transaction = item)
                else -> repository.updateItem(item)
            }

        }
    }

    private fun removeItem(item: FinanceDataEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            when (item) {
                is Transaction -> repository.removeTransaction(item)
                else -> repository.removeItem(item)
            }
        }
    }

}