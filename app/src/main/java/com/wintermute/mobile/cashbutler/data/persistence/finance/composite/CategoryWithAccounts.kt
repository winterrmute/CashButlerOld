package com.wintermute.mobile.cashbutler.data.persistence.finance.composite

import androidx.room.Embedded
import androidx.room.Relation
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Account
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinancialCategory

/**
 * Composite class representing financial categories with accounts belonging to ceratin categories.
 *
 * @author k.kosinski
 */
data class CategoryWithAccounts(
    @Embedded
    val category: FinancialCategory,

    @Relation(
        entity = Account::class,
        parentColumn = "id",
        entityColumn = "category_id"
    )
    val accounts: List<AccountWithTransactions>
)