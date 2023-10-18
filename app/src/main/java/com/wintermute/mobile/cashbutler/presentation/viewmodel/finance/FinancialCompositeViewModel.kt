package com.wintermute.mobile.cashbutler.presentation.viewmodel.finance

import android.icu.math.BigDecimal
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord
import com.wintermute.mobile.cashbutler.presentation.intent.FinancialRecordIntent
import com.wintermute.mobile.cashbutler.presentation.state.finance.FinancialDataCompositeState
import com.wintermute.mobile.cashbutler.presentation.state.finance.FinancialDataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinancialCompositeViewModel @Inject constructor(
    private val budgetViewModel: BudgetViewModel,
    private val expensesViewModel: ExpensesViewModel
) : ViewModel() {

    private val _state =
        MutableStateFlow(FinancialDataCompositeState(BigDecimal.ZERO, BigDecimal.ZERO))
    val state: StateFlow<FinancialDataCompositeState> = _state

    init {
        viewModelScope.launch {

            combine(budgetViewModel.state, expensesViewModel.state) { budgetState, expensesState ->
                Pair(budgetState, expensesState)
            }.collect { (budgetState, expensesState) ->
                if (budgetState is FinancialDataState.Initialized && expensesState is FinancialDataState.Initialized) {
                    handleInitializedState()
                }
            }
        }
    }

    private fun handleInitializedState() {
        val budgetState = budgetViewModel.state.value
        val expensesState = expensesViewModel.state.value

        if (budgetState is FinancialDataState.Initialized && expensesState is FinancialDataState.Initialized) {
            _state.value = FinancialDataCompositeState(
                budget = budgetState.balance,
                expenses = expensesState.balance
            )
        } else {
            // Wait for both ViewModels to be initialized
        }
    }
}