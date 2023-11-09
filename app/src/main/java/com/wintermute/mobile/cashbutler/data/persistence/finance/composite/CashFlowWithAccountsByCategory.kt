package com.wintermute.mobile.cashbutler.data.persistence.finance.composite

import androidx.room.Embedded
import androidx.room.Relation
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.CashFlow
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinancialCategory

/**
 * Composite class representing cash flow category with its accounts by category
 *
 * @author k.kosinski
 */
data class CashFlowWithAccountsByCategory(
    @Embedded
    val cashFlow: CashFlow,

    @Relation(
        entity = FinancialCategory::class,
        parentColumn = "category",
        entityColumn = "id"
    )
    val categoriesWithAccounts: List<CategoryWithAccounts>
)