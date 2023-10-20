package com.wintermute.mobile.cashbutler.presentation.view.components.finance

import android.icu.math.BigDecimal
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import arrow.core.Option
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord
import com.wintermute.mobile.cashbutler.presentation.view.components.core.input.CustomNumberField
import com.wintermute.mobile.cashbutler.presentation.view.components.core.input.LabeledInputField
import com.wintermute.mobile.cashbutler.presentation.view.components.ui.AddButton
import com.wintermute.mobile.cashbutler.presentation.view.components.ui.CancelButton

@Composable
fun FinanceRecordEntrySheet(
    record: Option<FinancialRecord>,
    onConfirm: (record: FinancialRecord) -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }


    Column(
        modifier = Modifier
            .height(500.dp)
            .padding(15.dp)
    ) {

        record.fold(
            ifSome = {
                title = it.title
                amount = it.amount.toString()
            },
            ifEmpty = {})

        LabeledInputField(label = "Title", value = title, onValueChange = { title = it })
        LabeledInputField(label = "Amount", value = amount, onValueChange = { amount = it })
        CustomNumberField()
//        LabeledInputField(label = "Date")
        AddButton {
            record.fold(
                ifEmpty = {
                    onConfirm(
                        FinancialRecord(
                            title = title,
                            amount = BigDecimal(amount),
                            category = -1L
                        )
                    )
                },
                ifSome = {
                    onConfirm(it.copy(title = title, amount = BigDecimal(amount)))
                }
            )


        }
        CancelButton {
            onDismiss()
        }

    }
}