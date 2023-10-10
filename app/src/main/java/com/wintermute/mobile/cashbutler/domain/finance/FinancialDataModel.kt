package com.wintermute.mobile.cashbutler.domain.finance

import com.wintermute.mobile.cashbutler.domain.finance.FinancialRecord

/**
 * Represents base profile of budget amount and expense states for a certain time period.
 *
 * @author k.kosinski
 */
data class FinancialDataModel(
    val budget: List<FinancialRecord>,
    val expenses: List<FinancialRecord>
)