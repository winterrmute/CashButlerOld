package com.wintermute.mobile.cashbutler.presentation.intent

/**
 * Defines user actions which can be dispatched to update the logic state.
 *
 * @author k.kosinski
 */
sealed class FinancialRecordIntent {
    data class AddFinanceCategory(val category: String): FinancialRecordIntent()

    data class AddRecord(val category: String, val title: String, val amount: String) :
        FinancialRecordIntent()

    class RemoveRecord(val category: String, val title: String) : FinancialRecordIntent()
}