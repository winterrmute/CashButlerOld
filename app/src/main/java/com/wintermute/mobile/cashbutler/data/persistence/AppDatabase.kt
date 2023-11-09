package com.wintermute.mobile.cashbutler.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.wintermute.mobile.cashbutler.data.persistence.converter.BigDecimalConverter
import com.wintermute.mobile.cashbutler.data.persistence.finance.dao.AccountDao
import com.wintermute.mobile.cashbutler.data.persistence.finance.dao.CashFlowDao
import com.wintermute.mobile.cashbutler.data.persistence.finance.dao.ExpensesDao
import com.wintermute.mobile.cashbutler.data.persistence.finance.dao.FinanceDataCompositeDao
import com.wintermute.mobile.cashbutler.data.persistence.finance.dao.FinancialCategoryDao
import com.wintermute.mobile.cashbutler.data.persistence.finance.dao.SavingsDao
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Account
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.CashFlow
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Expense
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Saving
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Transaction

/**
 * Application database.
 *
 * @author k.kosinski
 */
@Database(
    entities = [
        Account::class,
        CashFlow::class,
        Expense::class,
        FinancialCategory::class,
        Saving::class,
        Transaction::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(BigDecimalConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun financeDataCompositeDao(): FinanceDataCompositeDao
    abstract fun accountDao(): AccountDao
    abstract fun cashFlowDao(): CashFlowDao
    abstract fun expenseDao(): ExpensesDao
    abstract fun financialCategoryDao(): FinancialCategoryDao
    abstract fun savingsDao(): SavingsDao
}