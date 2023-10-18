package com.wintermute.mobile.cashbutler.presentation.state.finance

import android.icu.math.BigDecimal
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord

/**
 * Represents the hold state of financial records.
 *
 * @author k.kosinski
 */
sealed class FinancialDataState {
    object Uninitialized : FinancialDataState()
    data class Initialized(
        val financialRecords: Map<FinancialCategory, List<FinancialRecord>>,
        val balance: BigDecimal
    ) : FinancialDataState()

    data class Error(val errorMessage: String) : FinancialDataState()
}
