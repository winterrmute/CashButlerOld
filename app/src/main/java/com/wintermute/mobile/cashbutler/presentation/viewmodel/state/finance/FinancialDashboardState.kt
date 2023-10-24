package com.wintermute.mobile.cashbutler.presentation.viewmodel.state.finance

import android.icu.math.BigDecimal
import com.wintermute.mobile.cashbutler.presentation.view.components.reporting.DonutChartDataCollection

sealed class FinancialDashboardState {
    object Uninitialized : FinancialDashboardState()
    data class Initialized(
        val budget: BigDecimal,
        val expenses: BigDecimal,
        val balance: BigDecimal,
        val budgetData: DonutChartDataCollection,
        val expensesData: DonutChartDataCollection,
        val balanceData: DonutChartDataCollection
    ) : FinancialDashboardState()
    data class Error(val errorMessage: String): FinancialDashboardState()
}
