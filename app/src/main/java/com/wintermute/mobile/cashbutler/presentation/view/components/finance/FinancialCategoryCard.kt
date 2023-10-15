package com.wintermute.mobile.cashbutler.presentation.view.components.finance

import androidx.compose.runtime.Composable
import arrow.core.toOption
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord
import com.wintermute.mobile.cashbutler.presentation.view.components.core.ExpandableLabelCard

/**
 * Represents financial category enclosing certain financial records attached to this category.
 *
 * @param category information about this category
 * @param items list of current financial records (expenses in this case)
 * @param onConfirm action to make on confirm of adding an item
 */
@Composable
fun FinancialCategoryCard(
    category: FinancialCategory,
    items: List<FinancialRecord>,
    onConfirm: (title: String, amount: String) -> Unit,
) {

    ExpandableLabelCard(title = category.name) {
        items.toOption().fold(ifEmpty = {}, ifSome = {
            it.forEach { el ->
                FinanceRecordComponent(title = el.title, value = el.amount.toString())
            }
        })
        EditableFinanceRecordPanel(onConfirm = onConfirm)
    }
}

