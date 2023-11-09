package com.wintermute.mobile.cashbutler.presentation.view.components.finance.forms

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinanceDataEntity
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinancialCategory
import com.wintermute.mobile.cashbutler.domain.finance.FinancialCategories
import com.wintermute.mobile.cashbutler.domain.finance.ProposedCashFlowSources
import com.wintermute.mobile.cashbutler.domain.finance.ProposedExpenseSources
import com.wintermute.mobile.cashbutler.domain.finance.ProposedSavingsSources
import com.wintermute.mobile.cashbutler.presentation.intent.ProposedFinancialCategoriesIntent
import com.wintermute.mobile.cashbutler.presentation.view.components.core.input.InputComboBox
import com.wintermute.mobile.cashbutler.presentation.view.components.ui.AddButton
import com.wintermute.mobile.cashbutler.presentation.view.components.ui.CancelButton
import com.wintermute.mobile.cashbutler.presentation.viewmodel.components.ProposedFinancialCategoryDialogViewModel
import com.wintermute.mobile.cashbutler.presentation.viewmodel.state.components.ProposedFinancialCategoryDialogState

/**
 * TODO: Refactor me!
 */
@Composable
fun FinancialCategoryEditableForm(
    vm: ProposedFinancialCategoryDialogViewModel = hiltViewModel(),
    category: FinancialCategories,
    onConfirm: (FinanceDataEntity) -> Unit,
    onDismiss: () -> Unit
) {
    val vmState by vm.state.collectAsState();

    when (val state = vmState) {
        ProposedFinancialCategoryDialogState.Uninitialized -> {
            when (category) {
                FinancialCategories.CASH_FLOW -> {
                    vm.processIntent(
                        ProposedFinancialCategoriesIntent.InitState(
                            proposedItems = enumValues<ProposedCashFlowSources>().map { it.displayName },
                        )
                    )

                }

                FinancialCategories.SAVINGS -> {
                    vm.processIntent(
                        ProposedFinancialCategoriesIntent.InitState(
                            proposedItems = enumValues<ProposedSavingsSources>().map { it.displayName }
                        )
                    )
                }

                FinancialCategories.EXPENSES -> {
                    vm.processIntent(
                        ProposedFinancialCategoriesIntent.InitState(
                            proposedItems = enumValues<ProposedExpenseSources>().map { it.displayName }
                        )
                    )
                }
            }
        }

        is ProposedFinancialCategoryDialogState.Initialized -> {

            InputComboBox(items = state.items, onItemSelected = {
                vm.processIntent(ProposedFinancialCategoriesIntent.SetResult(FinancialCategory(title = it)))
            })
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                AddButton {
                    state.result.fold(
                        ifEmpty = {},
                        ifSome = {
                            onConfirm(it)
                        }
                    )
                }
                CancelButton {
                    onDismiss()
                }
            }
        }

        is ProposedFinancialCategoryDialogState.Error -> {
        }
    }
}