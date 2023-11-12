package com.wintermute.mobile.cashbutler.data.persistence.finance.composite

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Logical data representation of data hold in json for proposed categories with its related
 * proposed accounts.
 *
 * @author k.kosinski
 */
@JsonClass(generateAdapter = true)
data class ProposedCategoryWithAccounts(
    @Json(name = "category")
    val category: String,
    @Json(name = "accounts")
    val accounts: List<ProposedAccount>
)
