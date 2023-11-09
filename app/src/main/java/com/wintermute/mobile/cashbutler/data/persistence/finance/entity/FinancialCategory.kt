package com.wintermute.mobile.cashbutler.data.persistence.finance.entity

import android.icu.math.BigDecimal
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Represents a monetary category.
 *
 * @param id unique identifier.
 * @param title of this category.
 *
 * @author k.kosinski
 */
@Entity(tableName = "Categories")
data class FinancialCategory(
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0L,

    @ColumnInfo
    val title: String,

    @ColumnInfo
    val balance: BigDecimal = BigDecimal.ZERO
) : FinanceDataEntity