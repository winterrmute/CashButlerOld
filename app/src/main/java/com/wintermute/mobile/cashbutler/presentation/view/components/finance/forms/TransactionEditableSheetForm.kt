package com.wintermute.mobile.cashbutler.presentation.view.components.finance.forms

import android.os.Build
import androidx.compose.foundation.layout.Column
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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import arrow.core.Option
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Transaction
import com.wintermute.mobile.cashbutler.presentation.intent.TransactionSheetIntent
import com.wintermute.mobile.cashbutler.presentation.view.components.DatePreviewComponent
import com.wintermute.mobile.cashbutler.presentation.view.components.core.input.DatePickerComponent
import com.wintermute.mobile.cashbutler.presentation.view.components.core.input.LabeledInputField
import com.wintermute.mobile.cashbutler.presentation.view.components.dialog.BottomSheetDialogScaffold
import com.wintermute.mobile.cashbutler.presentation.viewmodel.components.TransactionSheetViewModel
import com.wintermute.mobile.cashbutler.presentation.viewmodel.state.components.TransactionEntrySheetState

/**
 * Form for editing or adding new transactions
 *
 * @param transaction optional item to edit
 * @param onConfirm action to take when user wants confirm creating new item or editing existing one
 * @param onDismiss action to abort the process
 */
@Composable
fun TransactionEditableSheetForm(
    vm: TransactionSheetViewModel = hiltViewModel(),
    transaction: Option<Transaction>,
    onConfirm: (Transaction) -> Unit,
    onDismiss: () -> Unit
) {
    val vmState = vm.state.collectAsState()

    when (val state = vmState.value) {
        is TransactionEntrySheetState.Error -> {
            Text(text = "ERROR")
        }

        is TransactionEntrySheetState.Initialized -> {
            var submit by remember { mutableStateOf(false) }
            var datePickerExpanded by remember { mutableStateOf(false) }

            BottomSheetDialogScaffold(
                onConfirm = {
                    vm.processIntent(TransactionSheetIntent.PrepareStoringTransaction)
                    submit = true
                },
                onDismiss = {
                    vm.processIntent(TransactionSheetIntent.ResetState)
                    onDismiss()
                }
            ) {
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
                    Text(
                        text = "New Transaction",
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )

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
            vm.processIntent(TransactionSheetIntent.InitState(transaction))
        }
    }
}