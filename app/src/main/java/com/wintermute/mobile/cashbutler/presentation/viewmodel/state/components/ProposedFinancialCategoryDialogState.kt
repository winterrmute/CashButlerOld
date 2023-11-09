package com.wintermute.mobile.cashbutler.presentation.viewmodel.state.components

import arrow.core.Option
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinanceDataEntity

sealed class ProposedFinancialCategoryDialogState {
    object Uninitialized : ProposedFinancialCategoryDialogState()
    data class Initialized(
        val items: List<String>,
        val result: Option<FinanceDataEntity>
    ) : ProposedFinancialCategoryDialogState()

    data class Error(val errorMessage: String) : ProposedFinancialCategoryDialogState()
}