package com.wintermute.mobile.cashbutler.presentation.view.components.finance

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.wintermute.mobile.cashbutler.R
import com.wintermute.mobile.cashbutler.presentation.intent.FinancialRecordIntent
import com.wintermute.mobile.cashbutler.presentation.viewmodel.finance.FinancialDataViewModel
import com.wintermute.mobile.cashbutler.presentation.view.components.core.CustomizableButton

@Composable
fun EditableFinanceRecordPanel(
    vm: FinancialDataViewModel = hiltViewModel(),
    category: String
) {
    var addingFinancialRecord by remember { mutableStateOf(false) }
    val context = LocalContext.current

    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White)
    ) {
        if (addingFinancialRecord) {
            EditableFinanceRecordComponent(
                onChangeTitle = { title = it },
                onChangeAmount = { amount = it })
        }
        Row {
            val value =
                if (addingFinancialRecord) context.getString(R.string.button_save) else context.getString(R.string.button_add)
            CustomizableButton(value = value, buttonColor = Color(123, 175, 228)) {
                if (addingFinancialRecord) {
                    vm.processIntent(
                        FinancialRecordIntent.AddRecord(
                            category,
                            title,
                            amount
                        )
                    )
                }

                addingFinancialRecord = !addingFinancialRecord
            }

            if (addingFinancialRecord) {
                CustomizableButton(value = "CANCEL", buttonColor = Color.DarkGray) {
                    addingFinancialRecord = false
                }
            }
        }
    }
}