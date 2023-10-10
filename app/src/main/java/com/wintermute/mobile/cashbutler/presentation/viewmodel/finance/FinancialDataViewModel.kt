package com.wintermute.mobile.cashbutler.presentation.viewmodel.finance

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.LocalViewModelStoreOwner
import arrow.core.Option
import com.wintermute.mobile.cashbutler.R
import com.wintermute.mobile.cashbutler.domain.finance.FinancialRecord
import com.wintermute.mobile.cashbutler.presentation.intent.FinancialRecordIntent
import com.wintermute.mobile.cashbutler.presentation.state.finance.FinancialDataViewState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject


/**
 * Handles the financial data composition state.
 *
 * @author k.kosinski
 */
@HiltViewModel
class FinancialDataViewModel @Inject constructor(
    @ApplicationContext context: Context
) : ViewModel() {

    private val resourceKeys = listOf(
        context.getString(R.string.budget_category),
        context.getString(R.string.expenses_housing),
        context.getString(R.string.expenses_utilities),
        context.getString(R.string.expenses_transportation),
        context.getString(R.string.expenses_groceries),
        context.getString(R.string.expenses_dining_out),
        context.getString(R.string.expenses_entertainment)
    )

    private val initialState = resourceKeys.associateWith {
        Option.fromNullable(listOf<FinancialRecord>())
    }

    private val _financialViewState = MutableStateFlow(FinancialDataViewState(initialState))
    val financialViewState: StateFlow<FinancialDataViewState> = _financialViewState

    /**
     * Handles modyfing financial records done by user.
     *
     * @param intent user action
     */
    fun processIntent(intent: FinancialRecordIntent) {
        val newState = when (intent) {
            is FinancialRecordIntent.AddRecord -> {
                val newRecord = FinancialRecord(intent.title, intent.amount)

                val newRecords = _financialViewState.value.financialRecords.toMutableMap()
                val categoryList = newRecords.getOrPut(intent.category) {
                    Option.fromNullable(emptyList())
                }

                val updatedCategoryList = categoryList.map { it + newRecord }

                newRecords[intent.category] = updatedCategoryList

                val updatedViewState =
                    _financialViewState.value.copy(financialRecords = newRecords)
                FinancialDataViewState(financialRecords = updatedViewState.financialRecords)
            }

            is FinancialRecordIntent.RemoveRecord -> {
                val updatedViewState = _financialViewState.value.copy(
                    financialRecords = _financialViewState.value.financialRecords
                        .mapValues { (_, categoryList) ->
                            categoryList.map { record ->
                                record.filter { it.title != intent.title }
                            }
                        }
                )
                FinancialDataViewState(financialRecords = updatedViewState.financialRecords)
            }

            is FinancialRecordIntent.AddFinanceCategory -> {

                val updatedViewState = _financialViewState.value.copy(
                    financialRecords = _financialViewState.value.financialRecords.plus(
                        mapOf(
                            intent.category to Option.fromNullable(
                                listOf()
                            )
                        )
                    )
                )
                FinancialDataViewState(financialRecords = updatedViewState.financialRecords)
            }
        }
        _financialViewState.value = newState
    }


}