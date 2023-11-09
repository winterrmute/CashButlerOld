package com.wintermute.mobile.cashbutler.presentation.view.components.finance

import androidx.compose.runtime.Composable
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinancialCategory

/**
 * Component holding all categories by certain root category.
 *
 * @param items categories list
 * @param onItemClick action to execute on tap on a category element
 * @param onItemDelete action to execute when a category is deleted
 */
@Composable
fun FinancialCategoriesList(
    items: List<FinancialCategory>,
    onItemClick: (FinancialCategory) -> Unit,
    onItemDelete: (FinancialCategory) -> Unit,
) {


    items.forEach {
        FinancialCategoryCard(
            title = it.title,
            balance = it.balance.toString(),
            onClick = {
                onItemClick(it)
            },
            onDeleteClick = {
                onItemDelete(it)
            }
        )
    }
}