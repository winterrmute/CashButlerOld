package com.wintermute.mobile.cashbutler.presentation.viewmodel.finance

import android.icu.math.BigDecimal
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wintermute.mobile.cashbutler.data.FinancialDataRepository
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord
import com.wintermute.mobile.cashbutler.domain.finance.FinancialCategories
import com.wintermute.mobile.cashbutler.presentation.intent.FinancialRecordIntent
import com.wintermute.mobile.cashbutler.presentation.state.finance.FinancialDataState
import com.wintermute.mobile.cashbutler.presentation.state.finance.FinancialDataState.Initialized
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
                val preparedRecords = records.mapValues { (_, records) -> records }
                    .map { (key, value) ->
                        key.copy(balance = calculateBalance(value)) to value
                    }.toMap()
                val balance = calculateBalance(preparedRecords.values.flatten())
                _state.value =
                    Initialized(
                        financialRecords = preparedRecords,
//                        records.mapValues { (_, records) -> records },
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


    override fun processIntent(intent: FinancialRecordIntent) {
        when (intent) {
            is FinancialRecordIntent.AddRecord -> {
                addRecord(intent)
            }

            is FinancialRecordIntent.RemoveRecord -> {
                removeRecord(intent)
            }

            is FinancialRecordIntent.AddFinanceCategory -> {
                addCategory(intent)
            }

            is FinancialRecordIntent.RemoveCategory -> {
                removeCategory(intent)
            }
        }
    }

    override fun addCategory(intent: FinancialRecordIntent.AddFinanceCategory) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.storeCategory(category = intent.category)
        }
    }


    override fun removeCategory(intent: FinancialRecordIntent.RemoveCategory) {
        viewModelScope.launch(Dispatchers.IO) { repository.removeCategory(intent.category) }
    }

    override fun addRecord(intent: FinancialRecordIntent.AddRecord) {
        var newRecord = FinancialRecord(
            title = intent.record.title,
            amount = intent.record.amount,
            category = intent.category.id
        )

        viewModelScope.launch(Dispatchers.IO) {
            val id = repository.storeRecord(newRecord)
            newRecord = newRecord.copy(id = id)
        }
    }

    override fun removeRecord(intent: FinancialRecordIntent.RemoveRecord) {
        when (_state.value) {
            is Initialized -> {
                repository.removeRecord(intent.record)
                TODO("Handle error")
            }

            else -> {
                TODO("Handle uninitialized state or error")
            }
        }
    }
}