package com.wintermute.mobile.cashbutler.presentation.viewmodel.state.components

import android.icu.math.BigDecimal
import arrow.core.Option
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord

/**
 * State of sheet destinated for adding or editing records.
 *
 * @param hasErrors: result of sanitize check.
 * @param title: record title
 * @param amount: record amount
 * @param titleErrorMessage: error message related to the input text field for title
 * @param amountErrorMessage: error message related to the input text field for amount
 * @param financialRecordToEdit: optional record in case of edit existing one
 * @param resultFinancialRecord: result that should be stored
 *
 * @author k.kosinski
 */
data class FinanceRecordEntrySheetState(
    val hasErrors: Boolean = false,
    val title: String = "",
    val amount: String = "",
    val titleErrorMessage: Option<String> = Option.fromNullable(null),
    val amountErrorMessage: Option<String> = Option.fromNullable(null),
    val financialRecordToEdit: Option<FinancialRecord> = Option.fromNullable(null),
    val resultFinancialRecord: FinancialRecord = FinancialRecord(title = "", amount = BigDecimal.ZERO, category = -1L)
)