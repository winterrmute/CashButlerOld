package com.wintermute.mobile.cashbutler.presentation.view.finance

import androidx.compose.runtime.Composable
import com.wintermute.mobile.cashbutler.data.persistence.finance.CategoryWithRecords
import com.wintermute.mobile.cashbutler.presentation.view.components.core.CheckBoxItemsList
import com.wintermute.mobile.cashbutler.presentation.view.components.core.model.CheckBoxTextItemModel

@Composable
fun IncomeResourcesDialog(
    incomeResources: List<CategoryWithRecords>,
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
        CheckBoxTextItemModel(item, incomeResources.any { it.category.name == item })
    }

    CheckBoxItemsList(items = result, onItemSelected = onItemSelected)
}