package com.wintermute.mobile.cashbutler.presentation.state.finance

import android.icu.math.BigDecimal

data class FinancialDataCompositeState(
    val budget: BigDecimal,
    val expenses: BigDecimal
)