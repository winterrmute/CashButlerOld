package com.wintermute.mobile.cashbutler.presentation.view.components.finance.forms

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import arrow.core.Either
import arrow.core.Option
import com.wintermute.mobile.cashbutler.data.persistence.finance.composite.ProposedAccount
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Account
import com.wintermute.mobile.cashbutler.presentation.intent.NewAccountIntent
import com.wintermute.mobile.cashbutler.presentation.view.components.core.RadioButtonTextItemGroup
import com.wintermute.mobile.cashbutler.presentation.view.components.core.input.LabeledInputField
import com.wintermute.mobile.cashbutler.presentation.view.components.core.model.RadioButtonTextItemModel
import com.wintermute.mobile.cashbutler.presentation.view.components.dialog.BottomSheetDialogScaffold
import com.wintermute.mobile.cashbutler.presentation.viewmodel.components.AccountEditableSheetDialogViewModel
import com.wintermute.mobile.cashbutler.presentation.viewmodel.state.components.AccountEditableSheetDialogState

/**
 * Form view for adding new accounts.
 *
 * @param visible
 */
@Composable
fun AccountsEditableForm(
    vm: AccountEditableSheetDialogViewModel = hiltViewModel(),
    proposedItems: List<ProposedAccount>,
    onConfirm: (Either<String, Account>) -> Unit,
    onDismiss: () -> Unit
) {
    val vmState by vm.state.collectAsState()

    when (val state = vmState) {
        AccountEditableSheetDialogState.Uninitialized -> {
            vm.processIntent(
                NewAccountIntent.InitState(
                    proposedItems = proposedItems
                )
            )
        }

        is AccountEditableSheetDialogState.Initialized -> {
            var submit by remember { mutableStateOf(false) }

            if (submit) {
                state.accountTitleErrorMessage.fold(
                    ifEmpty = {
                        onConfirm(state.result)
                        vm.processIntent(NewAccountIntent.ResetState)
                        onDismiss()
                    },
                    ifSome = {}
                )
                submit = false
            }

            BottomSheetDialogScaffold(
                onConfirm = {
                    vm.processIntent(NewAccountIntent.ValidateResult)
                    submit = true
                },
                onDismiss = {
                    vm.processIntent(NewAccountIntent.ResetState)
                    onDismiss()
                }
            ) {
                CreateAccountPanel(
                    items = state.viewItems,
                    currentlySelectedItem = state.selectedItemTypeIndex,
                    accountTitle = state.accountTitle,
                    accountTitleErrorMessage = state.accountTitleErrorMessage,
                    accountType = state.accountType,
                    onItemSelected = {
                        vm.processIntent(NewAccountIntent.SelectItemType(it))
                    },
                    onAccountTitleChange = {
                        vm.processIntent(NewAccountIntent.UpdateItemsTitle(it))
                    }
                )
            }
        }

        is AccountEditableSheetDialogState.Error -> {
        }
    }
}


@Composable
private fun CreateAccountPanel(
    items: List<RadioButtonTextItemModel>,
    currentlySelectedItem: Int,
    accountTitle: String,
    accountTitleErrorMessage: Option<String>,
    accountType: String,
    onItemSelected: (String) -> Unit,
    onAccountTitleChange: (String) -> Unit
) {
    Column {
        if (currentlySelectedItem > -1 || items.isEmpty()) {
            EditableAccountSheet(
                accountTitle = accountTitle,
                accountTitleErrorMessage = accountTitleErrorMessage,
                accountType = accountType,
                onAccountTitleChange = onAccountTitleChange
            )
        } else {
            Text(
                text = "Select account type",
                style = TextStyle(
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )
            )
            RadioButtonTextItemGroup(
                items = items,
                selectedItem = currentlySelectedItem,
                onSelectedChange = onItemSelected
            )
        }
    }
}

@Composable
private fun EditableAccountSheet(
    accountTitle: String,
    accountTitleErrorMessage: Option<String>,
    accountType: String,
    onAccountTitleChange: (String) -> Unit
) {
    Column {
        Text(
            text = accountType,
            style = TextStyle(
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )
        )
        LabeledInputField(
            label = "Title",
            value = accountTitle,
            errorMessage = accountTitleErrorMessage,
            onValueChange = onAccountTitleChange
        )
    }
}