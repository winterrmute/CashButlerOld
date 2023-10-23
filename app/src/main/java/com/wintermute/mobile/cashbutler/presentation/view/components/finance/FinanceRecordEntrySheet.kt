package com.wintermute.mobile.cashbutler.presentation.view.components.finance

import android.icu.math.BigDecimal
import android.icu.text.DecimalFormat
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import arrow.core.Option
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord
import com.wintermute.mobile.cashbutler.presentation.view.components.core.input.LabeledInputField
import com.wintermute.mobile.cashbutler.presentation.view.components.ui.AddButton
import com.wintermute.mobile.cashbutler.presentation.view.components.ui.CancelButton

@Composable
fun FinanceRecordEntrySheet(
    record: Option<FinancialRecord>,
    onConfirm: (record: FinancialRecord) -> Unit,
    onDismiss: () -> Unit
) {
    record.fold(
        ifEmpty = {
            innerRecordEntryPanel(onConfirm = onConfirm, onDismiss = onDismiss)
        },
        ifSome = {
            innerRecordEntryPanel(
                record = record,
                recordTitle = it.title,
                recordAmount = it.amount.toString(),
                onConfirm = onConfirm,
                onDismiss = onDismiss,
            )
        }
    )
}

@Composable
private fun innerRecordEntryPanel(
    recordTitle: String = "",
    recordAmount: String = "",
    onConfirm: (record: FinancialRecord) -> Unit,
    onDismiss: () -> Unit,
    record: Option<FinancialRecord> = Option.fromNullable(null)
) {
    var title by remember { mutableStateOf(recordTitle) }
    var amount by remember { mutableStateOf(recordAmount) }

    var titleErrorMessage by remember { mutableStateOf("") }
    var amountErrorMessage by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .height(500.dp)
            .padding(15.dp)
    ) {
        LabeledInputField(
            label = "Title",
            value = title,
            onValueChange = {

                title = it
            },
            errorMessage = titleErrorMessage
        )
        LabeledInputField(
            label = "Amount",
            value = amount,
            onValueChange = { amount = it },
            errorMessage = amountErrorMessage
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AddButton {
                if (title.isEmpty()) {
                    titleErrorMessage = "Title must not be empty"
                }

                if (amount.isEmpty()) {
                    amountErrorMessage = "Amount must not be empty"
                }

                if (title.isNotEmpty() && amount.isNotEmpty()) {
                    record.fold(
                        ifEmpty = {
                            onConfirm(
                                FinancialRecord(
                                    title = title,
                                    amount = sanitizeToMoneyFormat(amount),
                                    category = -1L
                                )
                            )
                        },
                        ifSome = {
                            onConfirm(it.copy(title = title, amount = BigDecimal(amount)))
                        }
                    )
                }
            }
            CancelButton {
                onDismiss()
            }
        }
    }
}

fun sanitizeToMoneyFormat(input: String): BigDecimal {
    // Check if the input is already in money format
    val regex = Regex("^\\$?([1-9]\\d{0,2}(,\\d{3})*|0)(\\.\\d{2})?$")
    if (input.matches(regex)) {
        return BigDecimal(input.replace(",",".")) // Return the input if it's already in money format
    }

    // If not, sanitize the input to the money format
    var sanitizedInput = input.replace(Regex("[^\\d.]"), "") // Remove non-digit and non-decimal point characters

    // Add "00" if the string is a whole number
    if (!sanitizedInput.contains(".")) {
        sanitizedInput += ".00"
    }

    // Round to two decimal places if the input is longer
    val amount = BigDecimal(sanitizedInput).setScale(2, BigDecimal.ROUND_HALF_EVEN) // Set scale to 2 decimal places

    // Convert the amount to money format
    val decimal =  DecimalFormat("#,##0.00").format(amount)

    return BigDecimal(decimal.replace(",","."))
}
