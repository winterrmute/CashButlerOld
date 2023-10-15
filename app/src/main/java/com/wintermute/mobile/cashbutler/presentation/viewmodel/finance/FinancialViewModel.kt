package com.wintermute.mobile.cashbutler.presentation.viewmodel.finance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.Option
import arrow.core.toOption
import com.wintermute.mobile.cashbutler.data.FinancialDataRepository
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord
import com.wintermute.mobile.cashbutler.domain.finance.FinancialCategories
import com.wintermute.mobile.cashbutler.presentation.intent.FinancialRecordIntent
import com.wintermute.mobile.cashbutler.presentation.state.finance.FinancialDataViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
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

    private val _state = MutableStateFlow(FinancialDataViewState(emptyMap()))
    val state: StateFlow<FinancialDataViewState> = _state

    init {
        viewModelScope.launch(Dispatchers.IO) {
            retrieveData(repository.getFinancialData(category))
        }
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

    override fun retrieveData(
        recordsFlow: Flow<Map<FinancialCategory, List<FinancialRecord>>>,
    ) {
        //TODO: handle errors
        viewModelScope.launch(Dispatchers.IO) {
            recordsFlow.collect { records ->
                _state.value =
                    FinancialDataViewState(financialRecords = records.mapValues { (_, records) -> records })
            }
        }
    }

    override fun addCategory(intent: FinancialRecordIntent.AddFinanceCategory) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.storeCategory(category = intent.category)
        }
    }


    override fun removeCategory(intent: FinancialRecordIntent.RemoveCategory) {
        repository.removeCategory(intent.category)
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
        val categoryOption: Option<FinancialCategory> =
            _state.value.financialRecords.keys.find { it.id == intent.record.id }
                .toOption()

        categoryOption.fold(
            { _state.value.financialRecords },
            { category ->
                _state.value.financialRecords.mapValues { (key, records) ->
                    if (key == category) {
                        records.filter { it.id != intent.record.id }
                    } else {
                        records
                    }
                }
            }
        )
    }
}