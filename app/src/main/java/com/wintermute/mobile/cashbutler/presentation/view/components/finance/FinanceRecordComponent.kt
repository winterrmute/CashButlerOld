package com.wintermute.mobile.cashbutler.presentation.view.components.finance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun FinanceRecordComponent(title: String, value: String) {
    Box(
        modifier = Modifier
            .background(Color.White)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Box(modifier = Modifier.weight(0.6f)) {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color.Gray,
                            shape = RoundedCornerShape(bottomStart = 10.dp, topStart = 10.dp)
                        )
                        .padding(start = 15.dp)
                        .wrapContentSize(align = Alignment.CenterStart),
                    text = title,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Start,
                    color = Color.White
                )
            }
            Box(modifier = Modifier.weight(0.3f)) {
                Text(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            color = Color.Gray,
                            shape = RoundedCornerShape(bottomEnd = 10.dp, topEnd = 10.dp)
                        )
                        .padding(end = 15.dp)
                        .padding(start = 15.dp)
                        .wrapContentSize(align = Alignment.CenterEnd),
                    text = value,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End,
                    color = Color.White // Set the font color to white
                )
            }
        }
    }
}