package com.wintermute.mobile.cashbutler.presentation.view.components.core

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.wintermute.mobile.cashbutler.presentation.view.components.core.model.CheckBoxTextItemModel

/**
 * Component representing a group of items consisting of checkbox and string
 *
 * @param items to place in this group
 * @param onStateChanged action to take when a checkbox state changes
 */
@Composable
fun CheckBoxItemsGroup(
    items: List<CheckBoxTextItemModel>,
    onStateChanged: (CheckBoxTextItemModel) -> Unit
) {
    Column {
        items.forEach {
            CheckBoxTextItem(
                title = it.title,
                isSelected = it.isChecked,
                enabled = it.isEnabled,
                onCheckedChange = { newState ->
                    onStateChanged(
                        CheckBoxTextItemModel(
                            id = it.id,
                            title = it.title,
                            isChecked = newState,
                            isEnabled = it.isEnabled
                        )
                    )
                })
        }
    }
}