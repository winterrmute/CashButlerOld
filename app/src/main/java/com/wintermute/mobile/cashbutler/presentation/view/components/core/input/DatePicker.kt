package com.wintermute.mobile.cashbutler.presentation.view.components.core.input

import android.icu.text.SimpleDateFormat
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.util.Date
import java.util.Locale

/**
 * Date picker component
 *
 * @param expanded if the date picker is visible or not
 * @param onDateSelected action to take when a date is selected by user
 * @param onDismiss action to take if the process is canceled
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerComponent(
    expanded: Boolean,
    onDateSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    if (expanded) {
        val datePickerState = rememberDatePickerState(selectableDates = object : SelectableDates {
            override fun isSelectableDate(utcTimeMillis: Long): Boolean {
                return utcTimeMillis <= System.currentTimeMillis()
            }
        })
        val selectedDate = datePickerState.selectedDateMillis?.let {
            val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.GERMAN)
            formatter.format(Date(it))
        } ?: ""

        Column(modifier = Modifier.fillMaxSize()) {
            DatePickerDialog(
                onDismissRequest = { onDismiss() },
                confirmButton = {
                    Button(onClick = {
                        onDateSelected(selectedDate)
                        onDismiss()
                    }

                    ) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        onDismiss()
                    }) {
                        Text(text = "Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}
