package com.wintermute.mobile.cashbutler.presentation.view.finance

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wintermute.mobile.cashbutler.R
import com.wintermute.mobile.cashbutler.presentation.view.components.core.HeaderTitle
import com.wintermute.mobile.cashbutler.presentation.view.components.finance.ExpenseCategoryCard
import com.wintermute.mobile.cashbutler.presentation.viewmodel.finance.FinancialDataViewModel
import dagger.hilt.android.qualifiers.ApplicationContext

@Composable
fun ExpenseView(
    vm: FinancialDataViewModel = hiltViewModel(),
    @ApplicationContext context: Context = LocalContext.current.applicationContext
) {
    val recordsState by vm.financialViewState.collectAsState()
    val expenseRecords =
        recordsState.financialRecords.filter { it.key != context.getString(R.string.budget_category) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 5.dp, horizontal = 5.dp)
    ) {
        HeaderTitle(value = context.getString(R.string.expenses_category))

        expenseRecords.forEach {
            if (it.key != context.getString(R.string.budget_category)) {
                ExpenseCategoryCard(title = it.key)
            }
        }
    }
}