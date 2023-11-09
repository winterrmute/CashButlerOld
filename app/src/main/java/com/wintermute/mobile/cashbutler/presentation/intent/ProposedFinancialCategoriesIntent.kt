package com.wintermute.mobile.cashbutler.presentation.intent

import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinanceDataEntity

sealed class ProposedFinancialCategoriesIntent : IntentActivity {
    data class InitState(
        val proposedItems: List<String>
    ) : ProposedFinancialCategoriesIntent()

    data class SetResult(val result: FinanceDataEntity) : ProposedFinancialCategoriesIntent()

    object ResetState : ProposedFinancialCategoriesIntent()
}