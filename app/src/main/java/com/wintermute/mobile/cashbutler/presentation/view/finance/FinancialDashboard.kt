package com.wintermute.mobile.cashbutler.presentation.view.finance

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wintermute.mobile.cashbutler.presentation.viewmodel.finance.FinancialCompositeViewModel

@Composable
fun FinancialDashboard(
    vm: FinancialCompositeViewModel = hiltViewModel(),
) {

    val state by vm.state.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(rememberScrollState())
            .padding(vertical = 5.dp, horizontal = 5.dp)

    ) {
        Text(text = "BUDGET: ${state.budget}")
        Text(text = "EXPENSES: ${state.expenses}")
    }
}