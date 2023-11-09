package com.wintermute.mobile.cashbutler.presentation.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import arrow.core.Option

/**
 * Represents a date preview component with button to start datepicker dialog.
 *
 * @param date the current date
 * @param errorMessage error message to show when date is wrong
 * @param onDateEditClick action to do on button to edit the date
 */
@Composable
fun DatePreviewComponent(
    date: String,
    errorMessage: Option<String>,
    onDateEditClick: () -> Unit
) {
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

    Column {
        Text(text = "Date")
        Box(
            modifier = Modifier
                .height(52.dp)
                .width(200.dp)
                .background(
                    color = Color.LightGray,
                    shape = RoundedCornerShape(8.dp)
                ),
            contentAlignment = Alignment.CenterStart
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(10.dp)
                        .clickable { onDateEditClick() },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = date,
                        modifier = Modifier.padding(8.dp),
                        style = TextStyle(color = color)
                    )
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Select date"
                    )
                }
            }
        }

        Text(
            text = errMessage,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Normal,
                color = Color.Red
            )
        )
    }
}
