package com.wintermute.mobile.cashbutler.presentation.view.finance

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import arrow.core.getOrElse
import com.wintermute.mobile.cashbutler.R
import com.wintermute.mobile.cashbutler.presentation.intent.FinancialRecordIntent
import com.wintermute.mobile.cashbutler.presentation.view.DialogWindow
import com.wintermute.mobile.cashbutler.presentation.view.components.core.CustomizableButton
import com.wintermute.mobile.cashbutler.presentation.view.components.core.HeaderTitle
import com.wintermute.mobile.cashbutler.presentation.view.components.finance.ExpenseCategoryCard
import com.wintermute.mobile.cashbutler.presentation.viewmodel.finance.FinancialDataViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

@Composable
fun IncomeView(
    vm: FinancialDataViewModel = hiltViewModel(),
    @ApplicationContext context: Context = LocalContext.current.applicationContext
) {
    val category = "Budget"
    var recordsDialogVisible by remember { mutableStateOf(false) }
    val recordsState by vm.financialViewState.collectAsState()
    val records = recordsState.financialRecords[category]?.getOrElse { emptyList() }!!

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 5.dp, horizontal = 5.dp)
    ) {
        HeaderTitle(value = context.getString(R.string.budget_category))


        records.forEach { el ->
            ExpenseCategoryCard(title = el.title)
        }

        CustomizableButton(
            buttonColor = Color(123, 175, 228),
            value = context.getString(R.string.button_add)
        ) {
            recordsDialogVisible = true
        }

        if (recordsDialogVisible) {
            var selectedItems by remember { mutableStateOf(records.map { it.title }) }
            DialogWindow(
                content = {
                    IncomeResourcesDialog(incomeResources = records, onItemSelected = {
                        selectedItems = it
                    })
                },
                onConfirm = {
                    selectedItems.forEach {
                        if (records.none { r -> r.title == it }) {
                            vm.processIntent(FinancialRecordIntent.AddRecord(category, it, "0.0"))
                        }
                    }

                    records.forEach {
                        if (!selectedItems.contains(it.title)) {
                            vm.processIntent(FinancialRecordIntent.RemoveRecord(category, it.title))
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
}