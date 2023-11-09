package com.wintermute.mobile.cashbutler.data.persistence.finance.composite

import androidx.room.Embedded
import androidx.room.Relation
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Account
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Transaction

/**
 * Composite class representing account with its transactions
 */
data class AccountWithTransactions(
    @Embedded
    val account: Account,

    @Relation(
        entity = Transaction::class,
        parentColumn = "id",
        entityColumn = "account_id"
    )
    val transactions: List<Transaction>
)
