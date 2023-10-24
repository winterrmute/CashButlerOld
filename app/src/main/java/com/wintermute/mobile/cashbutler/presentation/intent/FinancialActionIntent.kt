package com.wintermute.mobile.cashbutler.presentation.intent

import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord


/**
 * Defines user actions which can be dispatched to update the logic state.
 *
 * @author k.kosinski
 */
sealed class FinancialActionIntent: IntentActivity {
    /**
     * Intent creating new finance category
     *
     * @param category financial category
     */
    data class AddFinanceCategory(val category: FinancialCategory) : FinancialActionIntent()

    /**
     * Intent removing a financial category
     *
     * @param category target to remove
     */
    class RemoveCategory(val category: FinancialCategory) : FinancialActionIntent()

    /**
     * Intent creating new financial record inside category
     *
     * @param record target to persist
     */
    data class AddRecord(val record: FinancialRecord) : FinancialActionIntent()

    /**
     * Intent updating existing financial record
     *
     * @param record target to persist
     */
    data class UpdateRecord(val record: FinancialRecord) : FinancialActionIntent()

    /**
     * Intent removing a financial record
     *
     * @param record target record to remove
     */
    class RemoveRecord(val record: FinancialRecord) : FinancialActionIntent()
}