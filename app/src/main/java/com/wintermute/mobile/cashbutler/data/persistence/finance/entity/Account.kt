package com.wintermute.mobile.cashbutler.data.persistence.finance.entity

import android.icu.math.BigDecimal
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents an user account holding transaction history.
 *
 * @param id unique identifier.
 * @param categoryId to which this account is related to.
 * @param title of this account.
 *
 * @author k.kosinski
 */
@Entity(
    tableName = "Accounts",
    foreignKeys = [ForeignKey(
        entity = FinancialCategory::class,
        parentColumns = ["id"],
        childColumns = ["category_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["category_id"], unique = false)]
)
data class Account(
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0L,

    @ColumnInfo(name = "category_id")
    val categoryId: Long,

    @ColumnInfo
    val title: String,

    @ColumnInfo
    val balance: BigDecimal = BigDecimal.ZERO
) : FinanceDataEntity