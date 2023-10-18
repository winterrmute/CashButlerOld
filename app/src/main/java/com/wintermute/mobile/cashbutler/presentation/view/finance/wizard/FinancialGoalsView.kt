package com.wintermute.mobile.cashbutler.presentation.view.finance.wizard

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.wintermute.mobile.cashbutler.presentation.view.components.core.CustomizableButton

@Composable
fun FinancialGoalsView(
    onFinish: () -> Unit
) {
    Column {
        Text(text = "TODO")

        CustomizableButton(value = "FINISH") {
            onFinish()
        }
    }
}