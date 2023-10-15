package com.wintermute.mobile.cashbutler.presentation.state.finance

import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord

/**
 * Represents the hold state of financial records.
 *
 * @author k.kosinski
 */
data class FinancialDataViewState(
    val financialRecords: Map<FinancialCategory, List<FinancialRecord>>,
)