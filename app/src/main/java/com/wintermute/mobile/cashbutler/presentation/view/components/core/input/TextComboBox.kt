package com.wintermute.mobile.cashbutler.presentation.view.components.core.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TextComboBox(items: List<String>, onSelected: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }
    var text by remember { mutableStateOf("SELECT") }

    Column {
        BasicTextField(
            value = text,
            enabled = false,
            onValueChange = {
                text = it
            },
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    expanded = true
                }
                .padding(16.dp)
        )

        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(item) },
                    onClick = {
                        text = item
                        expanded = false
                        onSelected()
                    }
                )
            }
        }
    }
}