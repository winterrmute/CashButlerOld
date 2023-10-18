package com.wintermute.mobile.cashbutler.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.wintermute.mobile.cashbutler.data.FinancialDataRepository
import com.wintermute.mobile.cashbutler.data.persistence.AppDatabase
import com.wintermute.mobile.cashbutler.presentation.viewmodel.finance.BudgetViewModel
import com.wintermute.mobile.cashbutler.presentation.viewmodel.finance.ExpensesViewModel
import com.wintermute.mobile.cashbutler.presentation.viewmodel.finance.FinancialViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Dependency injection module providing instances.
 *
 * @author k.kosinski
 */
@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideAppDb(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "CashButler"
    ).createFromAsset("sqlite.db")
        .build()

    @Provides
    fun provideBudgetViewModel(db: AppDatabase): BudgetViewModel {
        return BudgetViewModel(provideFinancialDataRepository(db))
    }

    @Provides
    fun provideExpenseViewModel(db: AppDatabase): ExpensesViewModel {
        return ExpensesViewModel(provideFinancialDataRepository(db))
    }

    @Provides
    fun provideFinancialCategoryDao(db: AppDatabase) = db.financialCategoryDao()

    @Provides
    fun provideFinancialRecordDao(db: AppDatabase) = db.financialRecordDao()

    @Provides
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    @Singleton
    fun provideFinancialDataRepository(db: AppDatabase): FinancialDataRepository {
        return FinancialDataRepository(
            categoryDao = db.financialCategoryDao(),
            recordDao = db.financialRecordDao()
        )
    }
}