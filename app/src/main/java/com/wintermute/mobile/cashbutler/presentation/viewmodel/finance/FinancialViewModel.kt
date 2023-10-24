package com.wintermute.mobile.cashbutler.presentation.viewmodel.finance

import android.icu.math.BigDecimal
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wintermute.mobile.cashbutler.data.FinancialDataRepository
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord
import com.wintermute.mobile.cashbutler.domain.finance.FinancialCategories
import com.wintermute.mobile.cashbutler.presentation.intent.FinancialActionIntent
import com.wintermute.mobile.cashbutler.presentation.viewmodel.state.finance.FinancialDataState
import com.wintermute.mobile.cashbutler.presentation.viewmodel.state.finance.FinancialDataState.Initialized
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * Aggregation of managing financial actions made from views.
 *
 * @author k.kosinski
 */
abstract class FinancialViewModel(
    private val repository: FinancialDataRepository,
    private val category: FinancialCategories
) : ViewModel(), BaseFinancialViewModel {

    private val _state =
        MutableStateFlow<FinancialDataState>(FinancialDataState.Uninitialized).apply {
            retrieveData()
        }
    val state: StateFlow<FinancialDataState> = _state

    override fun retrieveData() {
        //TODO: handle errors
        viewModelScope.launch(Dispatchers.IO) {
            repository.getFinancialData(category).collect { records ->
                var balance = BigDecimal.ZERO
                val processedRecords = records.map {
                    val currentBalance = calculateBalance(it.records)
                    balance = balance.add(currentBalance)
                    it.copy(
                        category = it.category.copy(balance = currentBalance),
                        records = it.records
                    )
                }
                _state.value =
                    Initialized(
                        financialRecords = processedRecords,
                        balance = balance
                    )
            }
        }

    }

    private fun calculateBalance(records: List<FinancialRecord>): BigDecimal {
        var result = BigDecimal.ZERO
        records.forEach {
            result = result.add(it.amount)
        }
        return result
    }


    override fun processIntent(intent: FinancialActionIntent) {
        when (intent) {
            is FinancialActionIntent.AddRecord -> {
                addRecord(intent)
            }

            is FinancialActionIntent.UpdateRecord -> {
                updateRecord(intent)
            }

            is FinancialActionIntent.RemoveRecord -> {
                removeRecord(intent)
            }

            is FinancialActionIntent.AddFinanceCategory -> {
                addCategory(intent)
            }

            is FinancialActionIntent.RemoveCategory -> {
                removeCategory(intent)
            }

        }
    }

    override fun addCategory(intent: FinancialActionIntent.AddFinanceCategory) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.storeCategory(category = intent.category)
        }
    }


    override fun removeCategory(intent: FinancialActionIntent.RemoveCategory) {
        viewModelScope.launch(Dispatchers.IO) { repository.removeCategory(intent.category) }
    }

    override fun addRecord(intent: FinancialActionIntent.AddRecord) {
        var newRecord = FinancialRecord(
            title = intent.record.title,
            amount = intent.record.amount,
            category = intent.record.category
        )

        viewModelScope.launch(Dispatchers.IO) {
            val id = repository.storeRecord(newRecord)
            newRecord = newRecord.copy(id = id)
        }
    }

    override fun updateRecord(intent: FinancialActionIntent.UpdateRecord) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateRecord(record = intent.record)
        }
    }

    override fun removeRecord(intent: FinancialActionIntent.RemoveRecord) {
        viewModelScope.launch(Dispatchers.IO) { repository.removeRecord(intent.record) }
    }
}