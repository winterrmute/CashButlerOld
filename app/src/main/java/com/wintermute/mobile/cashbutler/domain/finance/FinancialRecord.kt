package com.wintermute.mobile.cashbutler.domain.finance

/**
 * Represents a financial record model which holds the value and its category.
 *
 * @author k.kosinski
 */
data class FinancialRecord(
    val title: String,
    val amount: String
)