package com.wintermute.mobile.cashbutler.presentation.view.components.core.input

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.toSize

@Composable
fun InputComboBox(
    items: List<String>,
    onItemSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    var textfieldSize by remember { mutableStateOf(Size.Zero) }
    val icon = Icons.Filled.ArrowDropDown

    Box(
        modifier = Modifier
            .height(500.dp)
            .padding(10.dp)
    ) {
        OutlinedTextField(
            value = selectedText,
            onValueChange = {
                selectedText = it
                onItemSelected(selectedText)
            },
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    //This value is used to assign to the DropDown the same width
                    textfieldSize = coordinates.size.toSize()
                },
            label = { Text("Title") },
            trailingIcon = {
                Icon(icon, "contentDescription",
                    Modifier.clickable { expanded = !expanded })
            }
        )
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier
                .width(with(LocalDensity.current) { textfieldSize.width.toDp() })
        ) {
            items.forEach { label ->
                DropdownMenuItem(
                    text = {
                        Text(text = label)
                    },
                    onClick = {
                        selectedText = label
                        onItemSelected(selectedText)
                        expanded = false
                    })
            }
        }
    }
}