package com.wintermute.mobile.cashbutler.data.persistence.converter

import android.icu.math.BigDecimal
import androidx.room.TypeConverter

/**
 * Converts big decimal from and to string for room db.
 *
 * @author k.kosinski
 */
class BigDecimalConverter {
    @TypeConverter
    fun fromString(value: String?): BigDecimal? {
        return value?.let { BigDecimal(it) }
    }

    @TypeConverter
    fun toString(value: BigDecimal?): String? {
        return value?.toString()
    }
}