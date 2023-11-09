package com.wintermute.mobile.cashbutler.data.persistence.finance.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents a financial cash flow information.
 *
 * @param id unique identifier
 * @param categories related categories ordered to cash flow information.
 *
 * @author k.kosinski
 */
@Entity(
    foreignKeys = [ForeignKey(
        entity = FinancialCategory::class,
        parentColumns = ["id"],
        childColumns = ["category"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["category"], unique = false)]
)
data class CashFlow(
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0L,

    @ColumnInfo
    val category: Long
) : FinanceDataEntity