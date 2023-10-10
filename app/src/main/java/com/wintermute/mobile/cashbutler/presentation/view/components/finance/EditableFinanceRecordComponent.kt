package com.wintermute.mobile.cashbutler.presentation.view.components.finance

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.wintermute.mobile.cashbutler.R
import com.wintermute.mobile.cashbutler.presentation.view.components.core.input.LabeledInputField

@Composable
fun EditableFinanceRecordComponent(
    title: String = "",
    onChangeTitle: (String) -> Unit = {},
    onChangeAmount: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val titleText = title.ifEmpty { context.getString(R.string.finance_record_title) }

    Row {
        Box(modifier = Modifier.weight(0.6f)) {
            LabeledInputField(
                label = titleText,
                modifier = Modifier.padding(5.dp),
                onValueChange = onChangeTitle
            )
        }
        Box(modifier = Modifier.weight(0.3f)) {
            LabeledInputField(
                label = context.getString(R.string.finance_record_amount),
                value = "0.0",
                modifier = Modifier.padding(5.dp),
                onValueChange = onChangeAmount
            )
        }
    }
}