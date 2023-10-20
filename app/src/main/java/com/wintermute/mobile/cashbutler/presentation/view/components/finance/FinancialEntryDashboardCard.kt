package com.wintermute.mobile.cashbutler.presentation.view.components.finance

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FinancialEntryDashboardCard(
    title: String,
    balance: String,
    onClick: () -> Unit,
    color: Color = Color.Black
) {
    Box(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .shadow(4.dp, shape = RoundedCornerShape(8.dp))
            .background(color = Color.White, shape = RoundedCornerShape(8.dp))
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row {
                Box(modifier = Modifier.weight(0.9f)) {
                    Column {
                        Text(
                            text = title,
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal,
                                color = color
                            ),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = balance,
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                color = color
                            )
                        )
                    }
                }
            }
        }
    }
}