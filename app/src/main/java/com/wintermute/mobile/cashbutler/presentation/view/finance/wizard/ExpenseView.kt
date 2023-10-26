package com.wintermute.mobile.cashbutler.presentation.view.finance.wizard

import android.content.Context
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
import com.wintermute.mobile.cashbutler.R
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord
import com.wintermute.mobile.cashbutler.domain.finance.FinancialCategories
import com.wintermute.mobile.cashbutler.presentation.intent.FinancialActionIntent
import com.wintermute.mobile.cashbutler.presentation.viewmodel.state.finance.FinancialDataState
import com.wintermute.mobile.cashbutler.presentation.view.components.core.HeaderTitle
import com.wintermute.mobile.cashbutler.presentation.view.components.finance.FinancialCategoryCard
import com.wintermute.mobile.cashbutler.presentation.view.components.ui.AddButton
import com.wintermute.mobile.cashbutler.presentation.view.finance.FinanceRecordPanel
import com.wintermute.mobile.cashbutler.presentation.view.finance.FinancialCategorySelectionDialog
import com.wintermute.mobile.cashbutler.presentation.viewmodel.finance.ExpensesViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

@Composable
fun ExpenseView(
    vm: ExpensesViewModel = hiltViewModel(),
    @ApplicationContext context: Context = LocalContext.current.applicationContext
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
        HeaderTitle(value = context.getString(R.string.expenses_category))

        when (val state = recordsState) {
            is FinancialDataState.Initialized -> {
                var selectedItems by remember { mutableStateOf(state.financialRecords.map { it.category.name }) }

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
                            vm.processIntent(FinancialActionIntent.RemoveCategory(it.category))
                            selectedItems = selectedItems.filter { item -> it.category.name != item }
                        }
                    )
                }

                if (categoryDetailViewVisible) {
                    FinanceRecordPanel(
                        category = state.financialRecords.first { it.category.id == currentCategory!!.id }.category,
                        data = state.financialRecords.first { it.category.id == currentCategory!!.id }.records,
                        onDismiss = { categoryDetailViewVisible = false },
                        onAdd = {
                            vm.processIntent(FinancialActionIntent.AddRecord(record = it))
                        },
                        onUpdate = {
                        },
                        onDelete = {
                            vm.processIntent(FinancialActionIntent.RemoveRecord(record = it))
                        }
                    )
                }

                AddButton {
                    recordsDialogVisible = true
                }

                if (recordsDialogVisible) {
                    FinancialCategorySelectionDialog(
                        category = FinancialCategories.EXPENSES,
                        currentlySelectedItems = selectedItems,
                        onConfirm = { result ->
                            recordsDialogVisible = false
                            selectedItems = result

                            selectedItems.forEach {
                                if (state.financialRecords.none { r -> r.category.name == it }) {
                                    vm.processIntent(
                                        FinancialActionIntent.AddFinanceCategory(
                                            //TODO: change parent id to dynamic value
                                            FinancialCategory(name = it, parent = 2L)
                                        )
                                    )
                                }
                            }

                            state.financialRecords.forEach {
                                if (!selectedItems.contains(it.category.name)) {
                                    vm.processIntent(FinancialActionIntent.RemoveCategory(it.category))
                                }
                            }
                        },
                        onDismiss = { recordsDialogVisible = false }
                    )
                }
            }

            is FinancialDataState.Error -> {}
            FinancialDataState.Uninitialized -> {}
        }
    }
}