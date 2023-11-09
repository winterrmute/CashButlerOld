package com.wintermute.mobile.cashbutler.presentation.view.components.finance

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun FinancialCategoryCard(
    title: String,
    balance: String,
    onClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(4.dp)
            .background(color = Color.White)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Row {
                Box(modifier = Modifier.weight(0.9f)) {
                    Column {
                        Text(
                            text = title,
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            text = balance,
                            style = androidx.compose.ui.text.TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Normal
                            )
                        )
                    }
                }
                Box(modifier = Modifier.weight(0.1f)) {
                    IconButton(onClick = { onDeleteClick() }) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete"
                        )
                    }
                }
            }

        }
    }
}