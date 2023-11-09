package com.wintermute.mobile.cashbutler.data.persistence.finance.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents expenses made by user.
 *
 * @param id unique identifier
 * @param categories related to expenses
 *
 * @author k.kosinski
 */
@Entity(
    tableName = "Expenses",
    foreignKeys = [ForeignKey(
        entity = Account::class,
        parentColumns = ["id"],
        childColumns = ["category"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["category"], unique = false)]
)
data class Expense(
    @PrimaryKey(autoGenerate = true)
    override val id: Long,

    @ColumnInfo
    val category: Long
) : FinanceDataEntity
