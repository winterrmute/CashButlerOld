package com.wintermute.mobile.cashbutler.data.persistence.finance.entity

import android.icu.math.BigDecimal
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents single transactions made by user.
 *
 * @param id unique identifier.
 * @param accountId to which this transactions belongs to.
 * @param title of this transaction.
 * @param date time when the transaction had been done or stored.
 * @param description of this transaction.
 *
 * @author k.kosinski
 */
@Entity(
    tableName = "Transactions",
    foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["account_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["account_id"], unique = false)]
)
data class Transaction(
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0L,

    @ColumnInfo(name = "account_id")
    val accountId: Long,

    @ColumnInfo
    val title: String,

    @ColumnInfo
    val amount: BigDecimal,

    @ColumnInfo
    val date: String,

    @ColumnInfo
    val description: String
) : FinanceDataEntity