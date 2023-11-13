package com.wintermute.mobile.cashbutler.presentation.viewmodel.state.finance

import com.wintermute.mobile.cashbutler.data.persistence.finance.composite.ProposedCategoryWithAccounts
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Account
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Transaction
import com.wintermute.mobile.cashbutler.domain.finance.FinancialCategories

/**
 * Represents a financial data state for a root category. CashFlow, Savings or Expenses.
 *
 * @author k.kosinski
 */
sealed class FinancialState {
    /**
     * Uninitialized state
     */
    object Uninitialized : FinancialState()

    /**
     * Error while initialization
     */
    data class Error(
        val errorMessage: String
    ) : FinancialState()

    /**
     * Fully initialized state.
     *
     * @param rootCategory root financial category
     * @param categories all categories belonging to the root category
     * @param accounts accounts grouped by the category id
     * @param transactions transactions grouped by the account id
     *
     */
    data class Initialized(
        val rootCategory: FinancialCategories,
        val categories: List<FinancialCategory>,
        val accounts: Map<Long, List<Account>>,
        val transactions: Map<Long, List<Transaction>>,
        val proposedItems: Map<String, ProposedCategoryWithAccounts>
    ) : FinancialState()
}