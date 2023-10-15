package com.wintermute.mobile.cashbutler.data.persistence.finance

import androidx.room.Embedded
import androidx.room.Relation

/**
 * Construct representing relational data between category and its records.
 *
 * @param category financial category
 * @param records related to the category
 *
 * @author k.kosinski
 */
data class CategoryWithRecords(
    @Embedded
    val category: FinancialCategory,

    @Relation(
        parentColumn = "financial_category_id",
        entityColumn = "category"
    )
    val records: List<FinancialRecord>
)
