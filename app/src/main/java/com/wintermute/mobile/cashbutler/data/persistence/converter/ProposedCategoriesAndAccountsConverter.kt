package com.wintermute.mobile.cashbutler.data.persistence.converter

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.adapter
import com.wintermute.mobile.cashbutler.data.persistence.finance.composite.ProposedCategoryWithAccounts

/**
 * Converter for proposed categories with proposed accounts for each category to string (database format)
 * and back to logical data layer necessary for business logic.
 *
 * @author k.kosinski
 */
@OptIn(ExperimentalStdlibApi::class)
class ProposedCategoriesAndAccountsConverter {

    /**
     * @param value json with array of categories and each category with array of its accounts
     */
    @TypeConverter
    fun fromString(value: String): Map<String, ProposedCategoryWithAccounts> {
        val moshi: Moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<List<ProposedCategoryWithAccounts>> = moshi.adapter()
        return jsonAdapter.fromJson(value)?.associateBy { it.category } ?: emptyMap()
    }

    /**
     * @param value logical data structure of proposed accounts related to proposed categories
     */
    @TypeConverter
    fun toString(value: Map<String, ProposedCategoryWithAccounts>): String{
        val moshi: Moshi = Moshi.Builder().build()
        val jsonAdapter: JsonAdapter<List<ProposedCategoryWithAccounts>> = moshi.adapter()
        return jsonAdapter.toJson(value.values.toList())
    }
}