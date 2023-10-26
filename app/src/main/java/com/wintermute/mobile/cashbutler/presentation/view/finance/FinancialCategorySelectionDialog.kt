package com.wintermute.mobile.cashbutler.presentation.view.finance

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.wintermute.mobile.cashbutler.domain.finance.FinancialCategories
import com.wintermute.mobile.cashbutler.presentation.intent.DefaultFinancialCategorySelectionIntent
import com.wintermute.mobile.cashbutler.presentation.view.DialogWindow
import com.wintermute.mobile.cashbutler.presentation.view.components.core.CheckBoxItemsList
import com.wintermute.mobile.cashbutler.presentation.viewmodel.components.DefaultFinancialCategorySelectionDialogViewModel
import com.wintermute.mobile.cashbutler.presentation.viewmodel.state.components.FinancialCategorySelectionDialogState

/**
 * Dialog to select default values for financial categories.
 *
 * @param category financial parent category (budget, or expense)
 * @param currentlySelectedItems items which the user already has persisted
 * @param onConfirm returns a list of items that should be persisted
 * @param onDismiss dismiss action
 */
@Composable
fun FinancialCategorySelectionDialog(
    vm: DefaultFinancialCategorySelectionDialogViewModel = hiltViewModel(),
    category: FinancialCategories,
    currentlySelectedItems: List<String>,
    onConfirm: (List<String>) -> Unit,
    onDismiss: () -> Unit
) {
    val viewState = vm.state.collectAsState()


    when (val state = viewState.value) {
        FinancialCategorySelectionDialogState.Uninitialized -> {
            vm.processIntent(
                DefaultFinancialCategorySelectionIntent.InitState(
                    category = category,
                    items = currentlySelectedItems
                )
            )
        }

        is FinancialCategorySelectionDialogState.Initialized -> {
            DialogWindow(
                content = {
                    CheckBoxItemsList(items = state.itemSelectionState, onItemSelected = {
                        vm.processIntent(DefaultFinancialCategorySelectionIntent.ToggleItem(it))
                    })
                },
                onConfirm = {
                    onConfirm(state.itemSelectionState.filter { it.isChecked }.map { it.title })
                    vm.processIntent(DefaultFinancialCategorySelectionIntent.DestroyState)
                },
                onDismiss = {
                    onDismiss()
                    vm.processIntent(DefaultFinancialCategorySelectionIntent.DestroyState)
                }
            )
        }

        is FinancialCategorySelectionDialogState.Error -> {}
    }
}