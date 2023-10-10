package com.wintermute.mobile.cashbutler.presentation.view.finance

import androidx.compose.runtime.Composable
import com.wintermute.mobile.cashbutler.domain.finance.FinancialRecord
import com.wintermute.mobile.cashbutler.presentation.view.components.core.CheckBoxItemsList
import com.wintermute.mobile.cashbutler.presentation.view.components.core.model.CheckBoxTextItemModel

@Composable
fun IncomeResourcesDialog(
    incomeResources: List<FinancialRecord>,
    onItemSelected: (List<String>) -> Unit
) {
    val items = listOf(
        "Income",
        "Investments",
        "PayPal",
        "Rental Income",
        "savings accounts",
        "cryptocurrency",
        "other"
    )

    val result: List<CheckBoxTextItemModel> = items.map { item ->
        CheckBoxTextItemModel(item, incomeResources.any { it.title == item })
    }

    CheckBoxItemsList(items = result, onItemSelected = onItemSelected)
}