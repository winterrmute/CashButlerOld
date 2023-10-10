package com.wintermute.mobile.cashbutler.presentation.view.components.core

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp

@Composable
fun CustomizableButton(buttonColor: Color = Color.Black, value: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier.padding(5.dp),
        onClick = {
            onClick()
        },
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(buttonColor)
    ) {
        Text(text = value)
    }
}