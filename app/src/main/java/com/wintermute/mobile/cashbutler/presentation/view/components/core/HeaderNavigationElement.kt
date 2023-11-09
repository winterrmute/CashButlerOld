package com.wintermute.mobile.cashbutler.presentation.view.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.wintermute.mobile.cashbutler.ui.theme.appBlue

/**
 * Represents an header text for certain UI-Section.
 *
 * @param title header text displayed
 */
@Composable
fun HeaderNavigationElement(
    title: String,
    subtitle: String,
    onAddClick: () -> Unit
) {

    Box(
        modifier = Modifier
            .padding(bottom = 2.dp)
            .fillMaxWidth()
            .height(86.dp)
            .shadow(16.dp)
            .background(color = Color.White)
    ) {
        Column(modifier = Modifier.padding(start = 10.dp)) {
            Text(
                text = title, style = TextStyle(
                    fontSize = 32.sp,
                    color = Color.Black,
                    fontStyle = FontStyle.Normal,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )
            Row {
                Text(
                    text = subtitle, style = TextStyle(
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Light,
                        textAlign = TextAlign.Start
                    )
                )
            }
        }
        LargeFloatingActionButton(
            modifier = Modifier
                .padding(
                    end = 16.dp,
                )
                .size(48.dp)
                .align(Alignment.CenterEnd),
            containerColor = appBlue,
            shape = CircleShape,
            onClick = { onAddClick() }
        ) {
            Icon(Icons.Filled.Add, "Large floating action button")
        }
    }
}