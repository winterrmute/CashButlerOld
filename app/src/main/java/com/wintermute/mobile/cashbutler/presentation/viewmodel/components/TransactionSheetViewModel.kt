package com.wintermute.mobile.cashbutler.presentation.viewmodel.components

import android.content.Context
import android.icu.math.BigDecimal
import androidx.lifecycle.ViewModel
import arrow.core.Option
import arrow.core.toOption
import com.wintermute.mobile.cashbutler.R
import com.wintermute.mobile.cashbutler.presentation.intent.TransactionSheetIntent
import com.wintermute.mobile.cashbutler.presentation.viewmodel.BaseViewModel
import com.wintermute.mobile.cashbutler.presentation.viewmodel.state.components.TransactionEntrySheetState
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class TransactionSheetViewModel @Inject constructor(
    @ApplicationContext val context: Context
) : ViewModel(), BaseViewModel<TransactionSheetIntent> {

    private var _state =
        MutableStateFlow<TransactionEntrySheetState>(TransactionEntrySheetState.Uninitialized)
    val state: StateFlow<TransactionEntrySheetState> = _state

    override fun processIntent(intent: TransactionSheetIntent) {
        when (intent) {
            is TransactionSheetIntent.UpdateAmountField -> {
                if (intent.amount.isNotEmpty()) {
                    _state.value = checkAmountForCorrectFormatOnInput(intent.amount)
                } else {
                    _state.value = (_state.value as TransactionEntrySheetState.Initialized).copy(
                        amount = intent.amount,
                        amountErrorMessage = Option.fromNullable(null)
                    )
                }
            }

            is TransactionSheetIntent.UpdateTitleField -> {
                if (intent.title.isNotEmpty()) {
                    _state.value = (_state.value as TransactionEntrySheetState.Initialized).copy(
                        title = intent.title,
                        titleErrorMessage = Option.fromNullable(null)
                    )
                } else {
                    _state.value =
                        (_state.value as TransactionEntrySheetState.Initialized).copy(title = intent.title)
                }
            }

            is TransactionSheetIntent.UpdateDate -> {
                _state.value =
                    (_state.value as TransactionEntrySheetState.Initialized).copy(
                        date = intent.date,
                        dateErrorMessage = Option.fromNullable(null)
                    )
            }

            is TransactionSheetIntent.UpdateDescription -> {
                _state.value =
                    (_state.value as TransactionEntrySheetState.Initialized).copy(description = intent.description)
            }

            is TransactionSheetIntent.PrepareStoringTransaction -> {
                _state.value =
                    checkFinancialRecordBeforeSubmit(
                        (_state.value as TransactionEntrySheetState.Initialized).title,
                        (_state.value as TransactionEntrySheetState.Initialized).amount,
                        (_state.value as TransactionEntrySheetState.Initialized).date,
                        (_state.value as TransactionEntrySheetState.Initialized).description
                    )
            }

            TransactionSheetIntent.ResetState -> {
                _state.value = TransactionEntrySheetState.Uninitialized
            }

            is TransactionSheetIntent.InitState -> {
                intent.transaction.fold(
                    ifEmpty = {
                        _state.value =
                            TransactionEntrySheetState.Initialized()
                    },
                    ifSome = {
                        _state.value =
                            TransactionEntrySheetState.Initialized(
                                title = it.title,
                                amount = it.amount.toString(),
                                date = it.date,
                                result = it
                            )
                    }
                )
            }
        }
    }

    private fun checkFinancialRecordBeforeSubmit(
        title: String,
        amount: String,
        date: String,
        description: String
    ): TransactionEntrySheetState {
        var hasErrors = false
        var titleErrorMessage = Option.fromNullable<String>(null)
        var amountErrorMessage = Option.fromNullable<String>(null)
        var dateErrorMessage = Option.fromNullable<String>(null)
        var descriptionErrorMessage = Option.fromNullable<String>(null)

        if (title.isEmpty()) {
            hasErrors = true
            titleErrorMessage =
                context.getString(R.string.transaction_sheet_title_empty).toOption()
        }

        if (amount.isEmpty()) {
            hasErrors = true
            amountErrorMessage =
                context.getString(R.string.transaction_sheet_amount_empty).toOption()
        }

        if (date.isEmpty()) {
            hasErrors = true
            dateErrorMessage =
                context.getString(R.string.transaction_sheet_date_empty).toOption()
        }

//        if (description.isEmpty()) {
//            hasErrors = true
//            descriptionErrorMessage =
//                context.getString(R.string.transaction_sheet_description_empty).toOption()
//        }

        if (checkAmountForInvalidFormat(amount) && amount.isNotEmpty()) {
            hasErrors = true
            amountErrorMessage =
                context.getString(R.string.transaction_sheet_amount_invalid_format).toOption()
        }

        if (!hasErrors) {
            val result = (_state.value as TransactionEntrySheetState.Initialized).result.copy(
                title = title,
                amount = BigDecimal(sanitizeToMoneyFormat(amount)),
                date = date
            )
            return (_state.value as TransactionEntrySheetState.Initialized).copy(
                hasErrors = false,
                title = title,
                amount = sanitizeToMoneyFormat(amount),
                date = date,
                titleErrorMessage = Option.fromNullable(null),
                amountErrorMessage = Option.fromNullable(null),
                dateErrorMessage = Option.fromNullable(null),
                descriptionErrorMessage = Option.fromNullable(null),
                result = result
            )
        }

        return (_state.value as TransactionEntrySheetState.Initialized).copy(
            hasErrors = true,
            titleErrorMessage = titleErrorMessage,
            amountErrorMessage = amountErrorMessage,
            dateErrorMessage = dateErrorMessage,
            descriptionErrorMessage = descriptionErrorMessage
        )
    }

    private fun checkAmountForCorrectFormatOnInput(amount: String): TransactionEntrySheetState {
        val hasError = !amount.matches(Regex("\\d[\\d,.]*"))
        val errorMessage = if (hasError) {
            Option.fromNullable(
                context.getString(
                    R.string.transaction_sheet_amount_invalid_format
                )
            )
        } else {
            Option.fromNullable(null)
        }

        return (_state.value as TransactionEntrySheetState.Initialized).copy(
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