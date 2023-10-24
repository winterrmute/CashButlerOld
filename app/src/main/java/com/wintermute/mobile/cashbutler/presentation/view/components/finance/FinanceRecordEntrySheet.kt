package com.wintermute.mobile.cashbutler.presentation.view.components.finance

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import arrow.core.Option
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord
import com.wintermute.mobile.cashbutler.presentation.intent.FinancialRecordIntent
import com.wintermute.mobile.cashbutler.presentation.view.components.core.input.LabeledInputField
import com.wintermute.mobile.cashbutler.presentation.view.components.ui.AddButton
import com.wintermute.mobile.cashbutler.presentation.view.components.ui.CancelButton
import com.wintermute.mobile.cashbutler.presentation.viewmodel.components.FinanceRecordEntrySheetViewModel


/**
 * This sheet is responsible for adding/editing financial records.
 *
 * @param record optional existing record in case of editing existing record
 * @param onConfirm action that should be executed on confirm
 * @param onDismiss instruction how to dismiss the adding process.
 */
@Composable
fun FinanceRecordEntrySheet(
    vm: FinanceRecordEntrySheetViewModel = hiltViewModel(),
    record: Option<FinancialRecord>,
    onConfirm: (record: FinancialRecord) -> Unit,
    onDismiss: () -> Unit
) {
    var submit by remember { mutableStateOf(false) }
    val state = vm.state.collectAsState()

    record.fold(ifEmpty = {}, ifSome = {
        vm.processIntent(FinancialRecordIntent.PassFinancialRecord(it))
    })

    //will try to submit financial record after sanitizing the inputs.
    if (submit) {
        if (!state.value.hasErrors) {
            onConfirm(state.value.resultFinancialRecord)
            vm.processIntent(FinancialRecordIntent.ResetState)
        }
        submit = false
    }

    Column(
        modifier = Modifier
            .height(500.dp)
            .padding(15.dp)
    ) {
        LabeledInputField(
            label = "Title",
            value = state.value.title,
            onValueChange = {
                vm.processIntent(FinancialRecordIntent.UpdateTitleField(it))
            },
            errorMessage = state.value.titleErrorMessage
        )
        LabeledInputField(
            label = "Amount",
            value = state.value.amount,
            onValueChange = {
                vm.processIntent(FinancialRecordIntent.UpdateAmountField(it))
            },
            errorMessage = state.value.amountErrorMessage
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AddButton {
                vm.processIntent(FinancialRecordIntent.PrepareFinancialRecordSubmit)
                submit = true
            }
            CancelButton {
                vm.processIntent(FinancialRecordIntent.ResetState)
                onDismiss()
            }
        }
    }
}