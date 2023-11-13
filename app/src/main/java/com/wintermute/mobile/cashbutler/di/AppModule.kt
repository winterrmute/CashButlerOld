package com.wintermute.mobile.cashbutler.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.wintermute.mobile.cashbutler.data.persistence.AppDatabase
import com.wintermute.mobile.cashbutler.data.repository.CashFlowRepository
import com.wintermute.mobile.cashbutler.data.repository.FinancialDaoComposite
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
    @Singleton
    fun provideContext(application: Application): Context {
        return application
    }

    @Provides
    fun provideProposedCategoriesWithAccountsDao(db: AppDatabase) = db.proposedCategoriesAndAccountsDao()

    @Provides
    fun provideFinancialDataCompositeDao(db: AppDatabase) = db.financeDataCompositeDao()

    @Provides
    fun provideAccountDao(db: AppDatabase) = db.accountDao()

    @Provides
    fun provideCashFlowDao(db: AppDatabase) = db.cashFlowDao()


    @Provides
    fun provideExpenseDao(db: AppDatabase) = db.expenseDao()


    @Provides
    fun provideFinancialCategoryDao(db: AppDatabase) = db.financialCategoryDao()


    @Provides
    fun provideSavingsDao(db: AppDatabase) = db.savingsDao()

    @Provides
    fun provideFinancialDaoComposite(db: AppDatabase): FinancialDaoComposite {
        return FinancialDaoComposite(
            financialCategoryDao = db.financialCategoryDao(),
            accountDao = db.accountDao(),
        )
    }

    @Provides
    @Singleton
    fun provideCashFlowRepository(db: AppDatabase): CashFlowRepository {
        return CashFlowRepository(
            cashFlowDao = db.cashFlowDao(),
            proposedCategoriesAndAccountsDao = db.proposedCategoriesAndAccountsDao(),
            financialDaoComposite = FinancialDaoComposite(
                db.financialCategoryDao(),
                db.accountDao(),
            )
        )
    }
}