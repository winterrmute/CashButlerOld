package com.wintermute.mobile.cashbutler.presentation.view.components.core

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import com.wintermute.mobile.cashbutler.presentation.view.components.core.model.CheckBoxTextItemModel

@Composable
fun CheckBoxItemsList(
    items: List<CheckBoxTextItemModel>,
    onItemSelected: (CheckBoxTextItemModel) -> Unit
) {
    Column {
        items.forEach {
            CheckBoxTextItem(item = it, onCheckedChange = { newState ->
                onItemSelected(CheckBoxTextItemModel(title = it.title, isChecked = newState))
            })
        }
    }
}