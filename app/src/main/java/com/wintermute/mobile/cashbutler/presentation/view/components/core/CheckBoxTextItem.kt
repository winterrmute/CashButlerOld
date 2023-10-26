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
import com.wintermute.mobile.cashbutler.presentation.view.components.core.model.CheckBoxTextItemModel

@Composable
fun CheckBoxTextItem(
    item: CheckBoxTextItemModel,
    onCheckedChange: (Boolean) -> Unit
) {
    var checked by remember { mutableStateOf(item.isChecked) }

    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = checked,
            onCheckedChange = {
                checked = it
                onCheckedChange(checked)
            })
        Text(text = item.title)
    }
}