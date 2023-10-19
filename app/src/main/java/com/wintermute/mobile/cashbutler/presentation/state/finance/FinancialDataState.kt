package com.wintermute.mobile.cashbutler.presentation.state.finance

import android.icu.math.BigDecimal
import com.wintermute.mobile.cashbutler.data.persistence.finance.CategoryWithRecords

/**
 * Represents the hold state of financial records.
 *
 * @author k.kosinski
 */
sealed class FinancialDataState {
    object Uninitialized : FinancialDataState()
    data class Initialized(
        val financialRecords: List<CategoryWithRecords>,
        val balance: BigDecimal
    ) : FinancialDataState()

    data class Error(val errorMessage: String) : FinancialDataState()
}
