package com.wintermute.mobile.cashbutler.presentation.view.components.finance

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import arrow.core.getOrElse
import com.wintermute.mobile.cashbutler.presentation.viewmodel.finance.FinancialDataViewModel
import com.wintermute.mobile.cashbutler.presentation.view.components.core.ExpandableLabelCard

/**
 * Represents expenses category enclosing category entries.
 *
 * @param title category title
 * @param expenses list of current financial records (expenses in this case)
 */
@Composable
fun ExpenseCategoryCard(
    vm: FinancialDataViewModel = hiltViewModel(),
    title: String,
) {
    val recordsState by vm.financialViewState.collectAsState()
    val records = recordsState.financialRecords[title]

    ExpandableLabelCard(title = title) {
        if (records == null) {
            EditableFinanceRecordPanel(category = title)
        } else {
            records.fold(ifEmpty = {}, ifSome = {
                it.forEach { el ->
                    FinanceRecordComponent(title = el.title, value = el.amount)
                }
            })
            EditableFinanceRecordPanel(category = title)
        }
    }
}
