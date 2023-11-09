package com.wintermute.mobile.cashbutler.data.persistence.finance.dao

import android.icu.math.BigDecimal
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.CashFlow
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Transaction

/**
 * Dao managing the financial data updates with handling the chaining operations.
 *
 * @author k.kosinski
 */
@Dao
interface FinanceDataCompositeDao {

    /**
     * Inserts a category and orders assigns it to CashFlow
     *
     * @param category to assign to cash flow
     */
    @androidx.room.Transaction
    fun insertCategoryAndUpdateCashFlow(category: FinancialCategory) {
        insertCashFlow(CashFlow(category = insertCategory(category)))
    }

    /**
     * Inserts a new transaction and updates the account and its category balance.
     *
     * @param transaction to be persisted
     */
    @androidx.room.Transaction
    fun insertTransactionAndUpdateBalances(transaction: Transaction) {
        insertTransaction(transaction)
        updateAccountBalance(transaction.accountId, transaction.amount)
        updateCategoryBalance(transaction.accountId, transaction.amount)
    }

    /**
     * Updates an existing transaction and updates the account and its category balance.
     *
     * @param transaction to be changed
     */
    @androidx.room.Transaction
    fun updateTransactionAndBalances(transaction: Transaction) {
        updateTransaction(transaction)
        updateAccountBalance(transaction.accountId, transaction.amount)
        updateCategoryBalance(transaction.accountId, transaction.amount)
    }

    /**
     * Deletes transaction and updates the account and its category balance.
     *
     * @param transaction to be removed
     */
    @androidx.room.Transaction
    fun removeTransactionAndModifyBalances(transaction: Transaction) {
        removeTransaction(transaction)
        updateAccountBalanceOnDelete(transaction.accountId, transaction.amount)
        updateCategoryBalanceOnDelete(transaction.accountId, transaction.amount)
    }

    /**
     * Sums up the balance of an account and a new transaction.
     *
     * @param accountId to update its balance
     * @param amount to add to actual balance
     */
    @Query("UPDATE Accounts SET balance = balance + :amount WHERE id = :accountId")
    fun updateAccountBalance(accountId: Long, amount: BigDecimal)

    /**
     * Sums up the balance of an category and a new transaction.
     *
     * @param accountId to update the balance by the account which is assigned to this category
     * @param amount to add to actual balance
     */
    @Query("UPDATE Categories SET balance = balance + :amount WHERE id = (SELECT category_id FROM Accounts WHERE id = :accountId)")
    fun updateCategoryBalance(accountId: Long, amount: BigDecimal)

    /**
     * Substracts the amount of transactoin from the balance of an account.
     *
     * @param accountId to update its balance
     * @param amount to reduce the balance by its value
     */
    @Query("UPDATE Accounts SET balance = balance - :amount WHERE id = :accountId")
    fun updateAccountBalanceOnDelete(accountId: Long, amount: BigDecimal)

    /**
     * Substracts the amount of transactoin from the balance of an account.
     *
     * @param accountId to update the balance by the account which is assigned to this category
     * @param amount to reduce the balance by its value
     */
    @Query("UPDATE Categories SET balance = balance - :amount WHERE id = (SELECT category_id FROM Accounts WHERE id = :accountId)")
    fun updateCategoryBalanceOnDelete(accountId: Long, amount: BigDecimal)


    @Insert
    fun insertCashFlow(traget: CashFlow): Long

    @Insert
    fun insertCategory(target: FinancialCategory): Long

    @Insert
    fun insertTransaction(target: Transaction): Long

    @Update
    fun updateTransaction(target: Transaction)

    @Delete
    fun removeTransaction(target: Transaction)
}