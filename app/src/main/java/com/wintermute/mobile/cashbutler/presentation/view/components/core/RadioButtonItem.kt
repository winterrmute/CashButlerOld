package com.wintermute.mobile.cashbutler.presentation.view.components.core

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * Component representing radio button with text
 *
 * @param title item's text
 * @param isSelected selection state
 * @param onSelectedChange action to take if the selected state changes
 */
@Composable
fun RadioButtonTextItem(
    title: String,
    isSelected: Boolean,
    onSelectedChange: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        RadioButton(
            selected = isSelected,
            onClick = onSelectedChange
        )
        Text(modifier = Modifier.clickable { onSelectedChange() }, text = title)
    }
}

