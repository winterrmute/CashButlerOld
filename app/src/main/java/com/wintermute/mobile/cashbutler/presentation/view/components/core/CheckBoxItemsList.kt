package com.wintermute.mobile.cashbutler.presentation.view.components.core

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.wintermute.mobile.cashbutler.presentation.view.components.core.model.CheckBoxTextItemModel

@Composable
fun CheckBoxItemsList(
    items: List<CheckBoxTextItemModel>,
    onItemSelected: (List<String>) -> Unit
) {
    var selectedItems by remember { mutableStateOf(items.filter { it.isChecked }.map { it.title }) }


    Column {
        items.forEach {
            CheckBoxTextItem(
                title = it.title,
                isSelected = it.isChecked,
                onCheckedChange = { isChecked ->
                    selectedItems = if (isChecked) {
                        selectedItems + it.title
                    } else {
                        selectedItems - it.title
                    }
                })
        }
        onItemSelected(selectedItems)
    }
}