package com.wintermute.mobile.cashbutler.presentation.view.components.core

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment

/**
 * Component representing a check box item with string
 *
 * @param title item text
 * @param isSelected item state
 * @param enabled item is clickable
 * @param onCheckedChange action to take when the item selection state changes
 */
@Composable
fun CheckBoxTextItem(
    title: String,
    isSelected: Boolean = false,
    enabled: Boolean = true,
    onCheckedChange: (Boolean) -> Unit
) {
    var checked by remember { mutableStateOf(isSelected) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checked,
            enabled = enabled,
            onCheckedChange = {
                checked = it
                onCheckedChange(checked)
            })
        Text(text = title)
    }
}