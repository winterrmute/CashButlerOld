package com.wintermute.mobile.cashbutler.presentation.view.finance

import android.icu.math.BigDecimal
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.wintermute.mobile.cashbutler.presentation.state.finance.FinancialDashboardState
import com.wintermute.mobile.cashbutler.presentation.view.components.finance.FinancialEntryDashboardCard
import com.wintermute.mobile.cashbutler.presentation.view.components.reporting.DonutChart
import com.wintermute.mobile.cashbutler.presentation.viewmodel.finance.FinancialDashboardViewModel
import com.wintermute.mobile.cashbutler.ui.theme.appGreen

@Composable
fun FinancialDashboard(
    vm: FinancialDashboardViewModel = hiltViewModel(),
) {
    val state by vm.state.collectAsState()

    when (val dataState = state) {
        is FinancialDashboardState.Initialized -> {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .padding(vertical = 5.dp, horizontal = 5.dp)

            ) {
                val actualChartData by remember { mutableStateOf(dataState.budgetData) }
                DonutChart(Modifier.padding(15.dp), data = actualChartData)
                { selected ->
                    AnimatedContent(targetState = selected, label = "") {
                        val amount = it?.amount ?: actualChartData.totalAmount
                        val text = it?.title ?: "Total"

                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("$${amount}")
                            Text(text)
                        }
                    }
                }

                FinancialEntryDashboardCard(
                    title = "BUDGET:",
                    balance = "${dataState.budget}",
                    onClick = { /*TODO*/ }
                )

                FinancialEntryDashboardCard(
                    title = "EXPENSES:",
                    balance = "${dataState.expenses}",
                    onClick = { /*TODO*/ })

                FinancialEntryDashboardCard(
                    title = "BALANCE:",
                    balance = "${dataState.balance}",
                    onClick = { /*TODO*/ },
                    color = if (dataState.balance < BigDecimal.ZERO) {
                        Color.Red
                    } else {
                        appGreen
                    }
                )

            }
        }

        is FinancialDashboardState.Error -> {}
        is FinancialDashboardState.Uninitialized -> {}
    }
}