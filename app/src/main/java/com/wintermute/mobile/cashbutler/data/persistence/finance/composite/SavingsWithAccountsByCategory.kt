package com.wintermute.mobile.cashbutler.data.persistence.finance.composite

import androidx.room.Embedded
import androidx.room.Relation
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Saving

/**
 * Represents single transactions made by user.
 *
 * @param id unique identifier
 * @param title of this transaction
 * @param date time when the transaction had been done or stored.
 * @param description of this transaction.
 *
 * @author k.kosinski
 */
data class SavingsWithAccountsByCategory(
    @Embedded
    val saving: Saving,

    @Relation(
        entity = FinancialCategory::class,
        parentColumn = "category",
        entityColumn = "id"
    )
    val accounts: List<CategoryWithAccounts>
)
