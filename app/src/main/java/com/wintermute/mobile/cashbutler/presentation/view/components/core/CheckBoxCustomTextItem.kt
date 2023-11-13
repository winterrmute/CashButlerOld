package com.wintermute.mobile.cashbutler.presentation.view.components.core

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Checkbox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import arrow.core.Option
import com.wintermute.mobile.cashbutler.presentation.view.components.core.input.TransparentUnderlineTextField

/**
 * Component representing check box item with textfield for creating custom elements
 *
 * @param isSelected state of the element
 * @param onCheckedChange action to take when the check state of item changes
 * @param onValueChange action to take when the actual value of the text item changes
 */
@Composable
fun CheckBoxCustomTextItem(
    isSelected: Boolean,
    errorMessage: Option<String>,
    onCheckedChange: (Boolean) -> Unit,
    onValueChange: (String) -> Unit
) {
    var checked by remember { mutableStateOf(isSelected) }

    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(
            checked = checked,
            onCheckedChange = {
                checked = it
                onCheckedChange(checked)
            })

        TransparentUnderlineTextField(
            enabled = checked,
            errorMessage = errorMessage,
            onValueChange = onValueChange,
            placeholder = "Other"
        )
    }
}