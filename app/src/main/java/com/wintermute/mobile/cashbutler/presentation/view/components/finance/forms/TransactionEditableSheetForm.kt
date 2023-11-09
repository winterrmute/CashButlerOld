package com.wintermute.mobile.cashbutler.presentation.view.components.finance.forms

import android.os.Build
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
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
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Account
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Transaction
import com.wintermute.mobile.cashbutler.presentation.intent.TransactionSheetIntent
import com.wintermute.mobile.cashbutler.presentation.view.components.DatePreviewComponent
import com.wintermute.mobile.cashbutler.presentation.view.components.core.input.DatePickerComponent
import com.wintermute.mobile.cashbutler.presentation.view.components.core.input.LabeledInputField
import com.wintermute.mobile.cashbutler.presentation.view.components.ui.AddButton
import com.wintermute.mobile.cashbutler.presentation.view.components.ui.CancelButton
import com.wintermute.mobile.cashbutler.presentation.viewmodel.components.TransactionSheetViewModel
import com.wintermute.mobile.cashbutler.presentation.viewmodel.state.components.TransactionEntrySheetState

@Composable
fun TransactionEditableSheetForm(
    vm: TransactionSheetViewModel = hiltViewModel(),
    visible: Boolean,
    transaction: Option<Transaction>,
    onConfirm: (Transaction) -> Unit,
    onDismiss: () -> Unit
) {
    val vmState = vm.state.collectAsState()
    if (visible) {
        var submit by remember { mutableStateOf(false) }

        when (val state = vmState.value) {
            is TransactionEntrySheetState.Error -> {
                Text(text = "ERROR")
            }

            is TransactionEntrySheetState.Initialized -> {
                var datePickerExpanded by remember { mutableStateOf(false) }

                Column {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                        DatePickerComponent(
                            expanded = datePickerExpanded,
                            onDateSelected = {
                                vm.processIntent(TransactionSheetIntent.UpdateDate(it))
                            },
                            onDismiss = { datePickerExpanded = false })
                    }
                    Column(
                        modifier = Modifier
                            .height(500.dp)
                            .fillMaxWidth()
                            .padding(15.dp)
                    ) {
                        LabeledInputField(
                            label = "Title",
                            value = state.title,
                            onValueChange = {
                                vm.processIntent(TransactionSheetIntent.UpdateTitleField(it))
                            },
                            errorMessage = state.titleErrorMessage
                        )
                        LabeledInputField(
                            label = "Amount",
                            value = state.amount,
                            onValueChange = {
                                vm.processIntent(TransactionSheetIntent.UpdateAmountField(it))
                            },
                            errorMessage = state.amountErrorMessage
                        )

                        DatePreviewComponent(
                            date = state.date,
                            errorMessage = state.dateErrorMessage,
                            onDateEditClick = { datePickerExpanded = true }
                        )

                        LabeledInputField(
                            modifier = Modifier.height(600.dp),
                            label = "Description",
                            value = state.description,
                            onValueChange = {
                                vm.processIntent(TransactionSheetIntent.UpdateDescription(it))
                            },
                            errorMessage = state.descriptionErrorMessage
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AddButton {
                            vm.processIntent(TransactionSheetIntent.PrepareStoringTransaction)
                            submit = true
                        }
                        CancelButton {
                            vm.processIntent(TransactionSheetIntent.ResetState)
                            onDismiss()
                        }
                    }
                }

                if (submit) {
                    if (!state.hasErrors) {
                        onConfirm(state.result)
                        vm.processIntent(TransactionSheetIntent.ResetState)
                    }
                    submit = false
                }
            }

            TransactionEntrySheetState.Uninitialized -> {
                Text(text = "INITIALIZING")
                vm.processIntent(TransactionSheetIntent.InitState(transaction))
            }
        }
    }
}