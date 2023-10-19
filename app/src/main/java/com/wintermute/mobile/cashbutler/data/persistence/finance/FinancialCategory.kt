package com.wintermute.mobile.cashbutler.data.persistence.finance

import android.icu.math.BigDecimal
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
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
data class FinancialCategory(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "financial_category_id")
    val id: Long = 0L,

    @ColumnInfo
    val name: String,

    @ColumnInfo
    val parent: Long,

    @Ignore
    var balance: BigDecimal = BigDecimal.ZERO
) {
    constructor(id: Long = 0L, name: String, parent: Long): this(id, name, parent, BigDecimal.ZERO)
}