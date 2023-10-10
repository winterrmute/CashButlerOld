package com.wintermute.mobile.cashbutler.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialCategoryDao
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialCategoryEntity
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecordDao
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecordEntity

/**
 * Application database.
 *
 * @author k.kosinski
 */
@Database(entities = [FinancialCategoryEntity::class, FinancialRecordEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
   abstract fun financialCategoryDao(): FinancialCategoryDao
   abstract fun financialRecordDao(): FinancialRecordDao
}