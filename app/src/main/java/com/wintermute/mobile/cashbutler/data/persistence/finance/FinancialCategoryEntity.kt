package com.wintermute.mobile.cashbutler.data.persistence.finance

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

/**
 * Represents entity of financial record category.
 *
 * @param id entry id
 * @param name category name
 * @param parent optional category parent if entry is a sub-category
 *
 * @author k.kosinski
 */
@Entity(
    tableName = "FinancialCategories",
    indices = [Index(value = ["name"], unique = true)]
)
data class FinancialCategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,

    @ColumnInfo
    val name: String,

    @ColumnInfo
    val parent: Long = -1L
)