package com.wintermute.mobile.cashbutler.data.repository

import com.wintermute.mobile.cashbutler.data.persistence.finance.composite.CategoryWithAccounts
import com.wintermute.mobile.cashbutler.data.persistence.finance.dao.BaseDao
import com.wintermute.mobile.cashbutler.data.persistence.finance.dao.CashFlowDao
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Account
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinanceDataEntity
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Transaction
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Repository for cash flow data.
 *
 * @author k.kosinski
 */
class CashFlowRepository @Inject constructor(
    private val cashFlowDao: CashFlowDao,
    private val financialDaoComposite: FinancialDaoComposite
) {

    /**
     * Gets all data for the cash flow root parent
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    fun getCashFlowData(): Flow<List<CategoryWithAccounts>> {
        return cashFlowDao.getAll().flatMapConcat {
            flow {
                val result = it
                    .flatMap { cashFlowAccountsByCategory -> cashFlowAccountsByCategory.categoriesWithAccounts }
                emit(result)
            }
        }
    }


    /**
     * Persits a new financial cetegory.
     */
    fun insertCategory(category: FinancialCategory) {
        cashFlowDao.insertCategoryAndUpdateCashFlow(category = category)
    }

    /**
     * Persists new transaction
     *
     * @param transaction to be persisted
     */
    fun insertTransaction(transaction: Transaction) {
        cashFlowDao.insertTransactionAndUpdateBalances(transaction)
    }

    /**
     * Updates existing transaction
     *
     * @param transaction to be modified
     */
    fun updateTransaction(transaction: Transaction) {
        cashFlowDao.updateTransactionAndBalances(transaction)
    }

    /**
     * Removes a transaction
     *
     * @param transaction to be removed
     */
    fun removeTransaction(transaction: Transaction) {
        cashFlowDao.removeTransactionAndModifyBalances(transaction)
    }


    /**
     * Adds an which is represents a financial data entry.
     *
     * @param item to be persisted
     */
    fun addItem(item: FinanceDataEntity) {
        val dao = determineDao(item = item) as BaseDao<FinanceDataEntity>
        dao.insert(item)
    }

    /**
     * Updates an item which is represents a financial data entry.
     *
     * @param item to be modified
     */
    fun updateItem(item: FinanceDataEntity) {
        val dao = determineDao(item = item) as BaseDao<FinanceDataEntity>
        dao.update(item)
    }

    /**
     * Removes an item which is represents a financial data entry.
     *
     * @param item to be removed
     */
    fun removeItem(item: FinanceDataEntity) {
        val dao = determineDao(item = item) as BaseDao<FinanceDataEntity>
        dao.delete(item)
    }

    private fun determineDao(item: FinanceDataEntity): BaseDao<*>? {
        return when (item) {
            is FinancialCategory -> financialDaoComposite.financialCategoryDao
            is Account -> financialDaoComposite.accountDao
            else -> {
                null
            }
        }
    }

}