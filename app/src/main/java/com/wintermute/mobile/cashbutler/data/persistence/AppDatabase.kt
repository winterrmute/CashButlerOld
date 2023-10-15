package com.wintermute.mobile.cashbutler.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wintermute.mobile.cashbutler.data.persistence.converter.BigDecimalConverter
import com.wintermute.mobile.cashbutler.data.persistence.finance.dao.FinancialCategoryDao
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.dao.FinancialRecordDao
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord

/**
 * Application database.
 *
 * @author k.kosinski
 */
@Database(entities = [FinancialCategory::class, FinancialRecord::class], version = 1, exportSchema = false)
@TypeConverters(BigDecimalConverter::class)
abstract class AppDatabase : RoomDatabase() {
   abstract fun financialCategoryDao(): FinancialCategoryDao
   abstract fun financialRecordDao(): FinancialRecordDao
}