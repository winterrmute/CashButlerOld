package com.wintermute.mobile.cashbutler.presentation.view.components.core

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ExpandableLabelCard(title: String, bodyItem: @Composable () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier =
        Modifier
            .padding(vertical = 5.dp)
            .background(
                color = Color(52, 168, 235),
                shape = RoundedCornerShape(10.dp)
            )
    ) {
        Column {
            CustomizableLabelCard(label = title, Modifier.clickable { expanded = !expanded })
            if (expanded) {
                bodyItem()
            }
        }
    }
}