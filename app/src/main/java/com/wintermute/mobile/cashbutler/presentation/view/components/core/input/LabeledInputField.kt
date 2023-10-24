package com.wintermute.mobile.cashbutler.presentation.view.components.core.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.sp
import arrow.core.Option

/**
 * Represents an input field with a label.
 *
 * @param label field title
 * @param value object value
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LabeledInputField(
    modifier: Modifier = Modifier,
    label: String,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    errorMessage: Option<String>
) {
    var text by remember { mutableStateOf(TextFieldValue(value)) }
    var color by remember { mutableStateOf(Color.Black) }
    var errMessage by remember { mutableStateOf("") }

    errorMessage.fold(
        ifEmpty = {
            color = Color.Black
            errMessage = ""
        },
        ifSome = {
            color = Color.Red
            errMessage = it
        }
    )


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
            .then(modifier)
    ) {
        Row {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = text,
                onValueChange = {
                    text = it
                    onValueChange(it.text)
                },
                label = { Text(label) },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = color
                )
            )
        }
        Text(
            text = errMessage,
            style = androidx.compose.ui.text.TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Red
            )
        )
    }
}