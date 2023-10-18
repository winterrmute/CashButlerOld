package com.wintermute.mobile.cashbutler.presentation.view.finance

import androidx.compose.runtime.Composable
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialCategory
import com.wintermute.mobile.cashbutler.presentation.view.components.core.CheckBoxItemsList
import com.wintermute.mobile.cashbutler.presentation.view.components.core.model.CheckBoxTextItemModel

@Composable
fun IncomeResourcesDialog(
    incomeResources: Set<FinancialCategory>,
    onItemSelected: (List<String>) -> Unit
) {
    //TODO: move to database
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
        CheckBoxTextItemModel(item, incomeResources.any { it.name == item })
    }

    CheckBoxItemsList(items = result, onItemSelected = onItemSelected)
}