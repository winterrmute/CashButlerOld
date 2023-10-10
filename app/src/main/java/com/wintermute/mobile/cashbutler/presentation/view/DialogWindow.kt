package com.wintermute.mobile.cashbutler.presentation.view

import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.wintermute.mobile.cashbutler.R
import dagger.hilt.android.qualifiers.ApplicationContext

@Composable
fun DialogWindow(
    content: @Composable () -> Unit,
    onDismiss: () -> Unit,
    onConfirm: () -> Unit,
    @ApplicationContext context: Context = LocalContext.current.applicationContext
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
                        Button(modifier = Modifier.padding(15.dp), onClick = { onConfirm() }) {
                            Text(text = context.getString(R.string.button_save))
                        }
                        Button(modifier = Modifier.padding(15.dp), onClick = { onDismiss() }) {
                            Text(text = context.getString(R.string.button_cancel))
                        }
                    }
                }
            }
        }
    }
}