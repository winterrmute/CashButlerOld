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
    onItemSelected: (CheckBoxTextItemModel) -> Unit
) {
    Column {
        items.forEach {
            CheckBoxTextItem(
                title = it.title,
                isSelected = it.isChecked,
                onCheckedChange = { newState ->
                    onItemSelected(CheckBoxTextItemModel(title = it.title, isChecked = newState))
                })
        }
    }
}