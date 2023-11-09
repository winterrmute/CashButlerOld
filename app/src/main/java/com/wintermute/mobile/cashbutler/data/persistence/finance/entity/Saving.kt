package com.wintermute.mobile.cashbutler.data.persistence.finance.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents user savings grouped in categories.
 *
 * @param id unique identifier
 * @param categories related to saving categories
 *
 * @author k.kosinski
 */
@Entity(
    tableName = "Savings",
    foreignKeys = [ForeignKey(
        entity = FinancialCategory::class,
        parentColumns = ["id"],
        childColumns = ["category"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["category"], unique = false)]
)
data class Saving(
    @PrimaryKey(autoGenerate = true)
    override val id: Long,

    @ColumnInfo
    val category: Long
) : FinanceDataEntity
