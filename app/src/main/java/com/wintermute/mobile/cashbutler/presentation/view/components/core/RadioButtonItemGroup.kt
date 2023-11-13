package com.wintermute.mobile.cashbutler.presentation.view.components.core

import androidx.compose.runtime.Composable
import com.wintermute.mobile.cashbutler.data.persistence.finance.composite.ProposedAccount
import com.wintermute.mobile.cashbutler.presentation.view.components.core.model.RadioButtonTextItemModel

/**
 * Group for radio button items with text
 *
 * @param items for this radio button group
 * @param selectedItem item's index
 * @param onSelectedChange action to take when the radio button's staate has changed
 */
@Composable
fun RadioButtonTextItemGroup(
    items: List<RadioButtonTextItemModel>,
    selectedItem: Int,
    onSelectedChange: (String) -> Unit
) {
    repeat(items.size) {
        val title = items[it].title
        val isSelected = selectedItem == it
        RadioButtonTextItem(
            title = title,
            isSelected = isSelected,
            onSelectedChange = { onSelectedChange(items[it].id)
            }
        )
    }
}