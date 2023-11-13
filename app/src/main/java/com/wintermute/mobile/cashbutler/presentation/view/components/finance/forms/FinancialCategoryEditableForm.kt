package com.wintermute.mobile.cashbutler.presentation.view.components.finance.forms

import androidx.compose.foundation.layout.Column
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
import com.wintermute.mobile.cashbutler.presentation.intent.NewFinancialCategoriesIntent
import com.wintermute.mobile.cashbutler.presentation.view.components.core.CheckBoxCustomTextItem
import com.wintermute.mobile.cashbutler.presentation.view.components.core.CheckBoxItemsGroup
import com.wintermute.mobile.cashbutler.presentation.view.components.dialog.BottomSheetDialogScaffold
import com.wintermute.mobile.cashbutler.presentation.viewmodel.components.FinancialCategoryEditableSheetDialogViewModel
import com.wintermute.mobile.cashbutler.presentation.viewmodel.state.components.FinancialCategoryEditableSheetDialogState
import java.util.UUID

/**
 * Component representing editable item panel for financial categories
 *
 * @param proposedItems which are proposed to user for certain category to give him a feeling
 * of usablity
 * @param ownedItems are items of proposed items which are alredy added by user
 * @param onConfirm action to take when user made his choises and want to confirm theses
 * @param onDismiss action to take on canceling the process
 */
@Composable
fun FinancialCategoryEditableForm(
    vm: FinancialCategoryEditableSheetDialogViewModel = hiltViewModel(),
    proposedItems: List<String>,
    ownedItems: List<String>,
    onConfirm: (List<String>) -> Unit,
    onDismiss: () -> Unit
) {

    val vmState by vm.state.collectAsState();
    when (val state = vmState) {
        FinancialCategoryEditableSheetDialogState.Uninitialized -> {
            vm.processIntent(
                NewFinancialCategoriesIntent.InitState(
                    proposedItems = proposedItems,
                    ownedItems = ownedItems
                )
            )
        }

        is FinancialCategoryEditableSheetDialogState.Initialized -> {
            //custom item id is needed to locate the item in results list and modify it
            var customItemId by remember { mutableStateOf("") }
            val customItemTitle by remember {
                mutableStateOf(
                    state.items.firstOrNull { it.id == customItemId }?.title ?: ""
                )
            }

            BottomSheetDialogScaffold(
                onConfirm = {
                    onConfirm(state.result.map { it.title })
                    vm.processIntent(NewFinancialCategoriesIntent.ResetState)
                    onDismiss()
                },
                onDismiss = {
                    vm.processIntent(NewFinancialCategoriesIntent.ResetState)
                    onDismiss()
                }
            ) {
                Column {
                    Text(
                        text = "Proposed Items",
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    CheckBoxItemsGroup(
                        items = state.items,
                        onStateChanged = {
                            vm.processIntent(
                                NewFinancialCategoriesIntent.ToggleItem(it)
                            )
                        }
                    )

                    Text(
                        modifier = Modifier.padding(top = 16.dp),
                        text = "Custom Item",
                        style = TextStyle(
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    CheckBoxCustomTextItem(
                        isSelected = false,
                        onCheckedChange = { isChecked ->
                            if (isChecked) {
                                customItemId = UUID.randomUUID().toString()
                                vm.processIntent(
                                    NewFinancialCategoriesIntent.AddCustomItem(
                                        id = customItemId,
                                        title = customItemTitle
                                    )
                                )
                            } else {
                                vm.processIntent(
                                    NewFinancialCategoriesIntent.RemoveCustomItem(id = customItemId)
                                )
                                customItemId = ""
                            }
                        },
                        onValueChange = {
                            vm.processIntent(
                                NewFinancialCategoriesIntent.ModifyCustomItemTitle(
                                    id = customItemId,
                                    newTitle = it
                                )
                            )
                        }
                    )
                }
            }
        }

        is FinancialCategoryEditableSheetDialogState.Error -> {
        }
    }
}