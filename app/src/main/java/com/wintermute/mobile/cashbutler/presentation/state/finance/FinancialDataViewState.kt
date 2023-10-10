package com.wintermute.mobile.cashbutler.presentation.state.finance

import arrow.core.Option
import com.wintermute.mobile.cashbutler.domain.finance.FinancialRecord

/**
 * Represents the hold state of financial records.
 *
 * @author k.kosinski
 */
data class FinancialDataViewState(
    val financialRecords: Map<String, Option<List<FinancialRecord>>>
)