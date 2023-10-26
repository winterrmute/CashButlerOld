package com.wintermute.mobile.cashbutler.domain.finance

/**
 * Default expense sources the user may choose.
 *
 * @author k.kosinski
 */
enum class DefaultExpenseSources(val displayName: String) {
    HOUSING("Housing"),
    UTILITIES("Utilities"),
    TRANSPORTATION("Transportation"),
    GROCERIES("Groceries"),
    DINING_OUT("Dining Out"),
    ENTERTAINMENT("Entertainment")
}