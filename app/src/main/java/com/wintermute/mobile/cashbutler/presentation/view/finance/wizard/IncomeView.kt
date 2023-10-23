package com.wintermute.mobile.cashbutler.presentation.view.finance.wizard

import android.content.Context
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import arrow.core.const
import com.wintermute.mobile.cashbutler.R
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord
import com.wintermute.mobile.cashbutler.presentation.intent.FinancialRecordIntent
import com.wintermute.mobile.cashbutler.presentation.state.finance.FinancialDataState
import com.wintermute.mobile.cashbutler.presentation.view.DialogWindow
import com.wintermute.mobile.cashbutler.presentation.view.components.core.HeaderTitle
import com.wintermute.mobile.cashbutler.presentation.view.components.finance.FinancialCategoryCard
import com.wintermute.mobile.cashbutler.presentation.view.components.ui.AddButton
import com.wintermute.mobile.cashbutler.presentation.view.finance.FinanceRecordPanel
import com.wintermute.mobile.cashbutler.presentation.view.finance.IncomeResourcesDialog
import com.wintermute.mobile.cashbutler.presentation.viewmodel.finance.BudgetViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

@Composable
fun IncomeView(
    vm: BudgetViewModel = hiltViewModel(),
    @ApplicationContext context: Context = LocalContext.current.applicationContext,
) {
    val recordsState by vm.state.collectAsState()

    var recordsDialogVisible by remember { mutableStateOf(false) }

    var categoryDetailViewVisible by remember { mutableStateOf(false) }
    var currentCategory: FinancialCategory? by remember { mutableStateOf(null) }
    var currentData by remember { mutableStateOf(emptyList<FinancialRecord>()) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 5.dp, horizontal = 5.dp)
    ) {
        HeaderTitle(value = context.getString(R.string.budget_category))

        when (val state = recordsState) {
            is FinancialDataState.Initialized -> {
                state.financialRecords.forEach {
                    FinancialCategoryCard(
                        title = it.category.name,
                        balance = it.category.balance.toString(),
                        onClick = {
                            currentCategory = it.category
                            currentData = it.records
                            categoryDetailViewVisible = true
                        },
                        onDeleteClick = {
                            vm.processIntent(FinancialRecordIntent.RemoveCategory(it.category))
                        }
                    )
                }

                if (categoryDetailViewVisible) {
                    FinanceRecordPanel(
                        category = state.financialRecords.first { it.category.id == currentCategory!!.id }.category,
                        data = state.financialRecords.first { it.category.id == currentCategory!!.id }.records,
                        onDismiss = { categoryDetailViewVisible = false },
                        onAdd = {
                            vm.processIntent(FinancialRecordIntent.AddRecord(record = it))
                        },
                        onUpdate = {
                            vm.processIntent(FinancialRecordIntent.UpdateRecord(record = it))
                        },
                        onDelete = {
                            vm.processIntent(FinancialRecordIntent.RemoveRecord(record = it))
                        }
                    )
                }

                AddButton {
                    recordsDialogVisible = true
                }

                if (recordsDialogVisible) {
                    var selectedItems by remember { mutableStateOf(state.financialRecords.map { it.category.name }) }
                    DialogWindow(
                        content = {
                            IncomeResourcesDialog(
                                incomeResources = state.financialRecords,
                                onItemSelected = {
                                    selectedItems = it
                                })
                        },
                        onConfirm = {
                            selectedItems.forEach {
                                if (state.financialRecords.none { r -> r.category.name == it }) {
                                    vm.processIntent(
                                        FinancialRecordIntent.AddFinanceCategory(
                                            FinancialCategory(name = it, parent = 1L)
                                        )
                                    )
                                }
                            }

                            state.financialRecords.forEach {
                                if (!selectedItems.contains(it.category.name)) {
                                    vm.processIntent(FinancialRecordIntent.RemoveCategory(it.category))
                                }
                            }
                            recordsDialogVisible = false
                        },
                        onDismiss = {
                            recordsDialogVisible = false
                        }
                    )
                }
            }

            is FinancialDataState.Error -> {}
            FinancialDataState.Uninitialized -> {}
        }
    }
}
