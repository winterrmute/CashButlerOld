package com.wintermute.mobile.cashbutler.data.persistence.finance.composite

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

/**
 * Logical data representation of proposed account json structure
 *
 * @author k.kosinski
 */
@JsonClass(generateAdapter = true)
data class ProposedAccount(
    @Json(name = "title")
    val title: String
)