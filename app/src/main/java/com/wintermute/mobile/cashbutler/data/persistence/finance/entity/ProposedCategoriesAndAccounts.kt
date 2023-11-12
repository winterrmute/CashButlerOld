package com.wintermute.mobile.cashbutler.data.persistence.finance.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.wintermute.mobile.cashbutler.data.persistence.finance.composite.ProposedCategoryWithAccounts

/**
 * This entity holds proposed categories and to each category related accounts which user would like
 * potentially observe in his finance tracking.
 *
 * @author k.kosinski
 */
@Entity
data class ProposedCategoriesAndAccounts(
    @PrimaryKey
    val id: Long = 0L,

    @ColumnInfo
    val rootCategory: String,

    @ColumnInfo
    val data: Map<String, ProposedCategoryWithAccounts>
)
