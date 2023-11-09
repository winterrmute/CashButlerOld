package com.wintermute.mobile.cashbutler.presentation.intent

import arrow.core.Option
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Transaction
import java.util.Date

sealed class TransactionSheetIntent : IntentActivity {

    data class InitState(val transaction: Option<Transaction>) : TransactionSheetIntent()

    /**
     * Updating the title of transaction
     *
     * @param title of transaction
     */
    data class UpdateTitleField(val title: String) : TransactionSheetIntent()

    /**
     * Updating the amount of transaction
     *
     * @param amount of transaction
     */
    data class UpdateAmountField(val amount: String) : TransactionSheetIntent()

    /**
     * Updating the date of transaction
     *
     * @param date of transaction
     */
    data class UpdateDate(val date: String): TransactionSheetIntent()

    /**
     * Updating the description of transaction
     *
     * @param description of transaction
     */
    data class UpdateDescription(val description: String): TransactionSheetIntent()

    /**
     * Intent to trigger check for valid formats and sanitizing the user input.
     */
    object PrepareStoringTransaction : TransactionSheetIntent()

    /**
     * Resetting the state.
     */
    object ResetState : TransactionSheetIntent()
}