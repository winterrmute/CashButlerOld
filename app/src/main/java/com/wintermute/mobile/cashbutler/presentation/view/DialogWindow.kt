package com.wintermute.mobile.cashbutler.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.wintermute.mobile.cashbutler.presentation.view.components.ui.AddButton
import com.wintermute.mobile.cashbutler.presentation.view.components.ui.CancelButton

@Composable
fun DialogWindow(
    content: @Composable () -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
) {
    val density = LocalDensity.current.density
    val dialogWidth = (80 * density).dp // 80% of screen width
    val dialogHeight = (70 * density).dp // 70% of screen height

    Dialog(
        onDismissRequest = { onDismiss() }
    ) {
        Surface(
            modifier = Modifier
                .padding(16.dp) // Add padding
                .sizeIn(minWidth = dialogWidth, minHeight = dialogHeight),
            shape = RoundedCornerShape(16.dp),
            color = Color.White
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column {
                    content()
                    Row {
                        AddButton {
                            onConfirm()
                        }

                        CancelButton {
                            onDismiss()
                        }
                    }
                }
            }
        }
    }
}