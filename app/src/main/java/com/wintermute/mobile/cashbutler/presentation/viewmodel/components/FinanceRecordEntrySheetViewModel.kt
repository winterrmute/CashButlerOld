package com.wintermute.mobile.cashbutler.presentation.viewmodel.components

import android.content.Context
import android.icu.math.BigDecimal
import androidx.lifecycle.ViewModel
import arrow.core.Option
import arrow.core.toOption
import com.wintermute.mobile.cashbutler.R
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord
import com.wintermute.mobile.cashbutler.presentation.intent.FinancialRecordIntent
import com.wintermute.mobile.cashbutler.presentation.viewmodel.BaseViewModel
import com.wintermute.mobile.cashbutler.presentation.viewmodel.state.components.FinanceRecordEntrySheetState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * This view model is responsible for sanitizing of input and managing the adding/editing financial
 * record state.
 *
 * @author k.kosinski
 */
@HiltViewModel
class FinanceRecordEntrySheetViewModel @Inject constructor(
    @ApplicationContext val context: Context
) : ViewModel(), BaseViewModel<FinancialRecordIntent> {

    private var _state = MutableStateFlow(FinanceRecordEntrySheetState())
    val state: StateFlow<FinanceRecordEntrySheetState> = _state

    override fun processIntent(intent: FinancialRecordIntent) {
        when (intent) {
            is FinancialRecordIntent.UpdateAmountField -> {
                if (intent.amount.isNotEmpty()) {
                    _state.value = checkAmountForCorrectFormatOnInput(intent.amount)
                } else {
                    _state.value = _state.value.copy(
                        amount = intent.amount,
                        amountErrorMessage = Option.fromNullable(null)
                    )
                }
            }

            is FinancialRecordIntent.UpdateTitleField -> {
                if (intent.title.isNotEmpty()) {
                    _state.value = _state.value.copy(
                        title = intent.title,
                        titleErrorMessage = Option.fromNullable(null)
                    )
                } else {
                    _state.value = _state.value.copy(title = intent.title)
                }
            }

            is FinancialRecordIntent.PrepareFinancialRecordSubmit -> {
                _state.value =
                    checkFinancialRecordBeforeSubmit(_state.value.title, _state.value.amount)

                if (!_state.value.hasErrors) {
                    _state.value = prepareSubmit(
                        _state.value.title,
                        _state.value.amount,
                        _state.value.financialRecordToEdit
                    )
                }
            }

            is FinancialRecordIntent.PassFinancialRecord -> {
                _state.value = _state.value.copy(
                    title = intent.record.title,
                    amount = intent.record.amount.toString(),
                    financialRecordToEdit = intent.record.toOption()
                )
            }

            FinancialRecordIntent.ResetState -> {
                _state.value = FinanceRecordEntrySheetState()
            }
        }
    }

    private fun prepareSubmit(
        title: String,
        amount: String,
        optionalFinancialRecord: Option<FinancialRecord>,
    ): FinanceRecordEntrySheetState {
        optionalFinancialRecord.fold(
            ifEmpty = {
                return _state.value.copy(
                    resultFinancialRecord = FinancialRecord(
                        title = title,
                        amount = BigDecimal(amount),
                        category = -1L
                    )
                )
            },
            ifSome = {
                return _state.value.copy(
                    resultFinancialRecord = it.copy(
                        title = title,
                        amount = BigDecimal(amount)
                    )

                )
            }
        )
    }

    private fun checkFinancialRecordBeforeSubmit(
        title: String,
        amount: String
    ): FinanceRecordEntrySheetState {
        var hasErrors = false
        var titleErrorMessage = Option.fromNullable<String>(null)
        var amountErrorMessage = Option.fromNullable<String>(null)

        if (title.isEmpty()) {
            hasErrors = true
            titleErrorMessage =
                context.getString(R.string.financial_record_sheet_title_empty).toOption()
        }

        if (amount.isEmpty()) {
            hasErrors = true
            amountErrorMessage =
                context.getString(R.string.financial_record_sheet_amount_empty).toOption()
        }

        if (checkAmountForInvalidFormat(amount) && amount.isNotEmpty()) {
            hasErrors = true
            amountErrorMessage =
                context.getString(R.string.financial_record_sheet_amount_invalid_format).toOption()
        }

        if (!hasErrors) {
            return _state.value.copy(
                hasErrors = false,
                titleErrorMessage = Option.fromNullable(null),
                amountErrorMessage = Option.fromNullable(null),
                amount = sanitizeToMoneyFormat(amount)
            )
        }

        return _state.value.copy(
            hasErrors = true,
            titleErrorMessage = titleErrorMessage,
            amountErrorMessage = amountErrorMessage
        )
    }

    private fun checkAmountForCorrectFormatOnInput(amount: String): FinanceRecordEntrySheetState {
        val hasError = !amount.matches(Regex("\\d[\\d,.]*"))
        val errorMessage = if (hasError) {
            Option.fromNullable(
                context.getString(
                    R.string.financial_record_sheet_amount_invalid_format
                )
            )
        } else {
            Option.fromNullable(null)
        }

        return _state.value.copy(
            hasErrors = hasError,
            amount = amount,
            amountErrorMessage = errorMessage
        )
    }

    private fun checkAmountForInvalidFormat(amount: String): Boolean {
        val pattern = Regex("[0-9]+([.,][0-9]+)?")
        return !amount.matches(pattern)
    }

    private fun sanitizeToMoneyFormat(input: String): String {
        var result = input.replace(",", ".")
        if (!result.contains(".")) {
            result = "$result.00"
        }

        return BigDecimal(result).setScale(
            2,
            BigDecimal.ROUND_DOWN
        ).toString()
    }
}