package com.wintermute.mobile.cashbutler.presentation.intent

import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord

/**
 * Intent aggregation for creating/editing financial records.
 *
 * @author k.kosinski
 */
sealed class FinancialRecordIntent : IntentActivity {

    /**
     * Updating the text field for title
     *
     * @param title of record
     */
    data class UpdateTitleField(val title: String): FinancialRecordIntent()

    /**
     * Updating the text field for amount
     *
     * @param amount of record
     */
    data class UpdateAmountField(val amount: String): FinancialRecordIntent()

    /**
     * Passing the existing record to edit to the state in view model
     *
     * @param record to be edited.
     */
    data class PassFinancialRecord(val record: FinancialRecord): FinancialRecordIntent()

    /**
     * Intent to trigger check for valid formats and sanitizing the user input.
     */
    object PrepareFinancialRecordSubmit: FinancialRecordIntent()

    /**
     * Resetting the state.
     */
    object ResetState: FinancialRecordIntent()
}