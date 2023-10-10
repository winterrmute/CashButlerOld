package com.wintermute.mobile.cashbutler.data.persistence.finance

import android.icu.math.BigDecimal
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents entity of financial record.
 *
 * @param id entry id
 * @param title financial record title
 * @param category of the financial record
 *
 * @author k.kosinski
 */
@Entity(
    tableName = "FinancialRecords",
    foreignKeys = [ForeignKey(
        entity = FinancialCategoryEntity::class,
        parentColumns = ["id"],
        childColumns = ["category"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["title"], unique = true)]
)
data class FinancialRecordEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo
    val title: String,

    @ColumnInfo
    val amount: BigDecimal,

    @ColumnInfo
    val category: Long = -1L
)