package com.wintermute.mobile.cashbutler.domain.finance

/**
 * Default income sources the user may choose.
 *
 * @author k.kosinski
 */
enum class DefaultIncomeSources(val displayName: String) {
    INCOME("Income"),
    INVESTMENTS("Investments"),
    PAYPAL("PayPal"),
    RENTAL_INCOME("Rental income"),
    SAVING_ACCOUNTS("Saving accounts"),
    CRYPTOCURRENCY("Cryptocurrency")
}