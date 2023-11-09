package com.wintermute.mobile.cashbutler.presentation.view.components.finance

import androidx.compose.runtime.Composable
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Account
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinanceDataEntity
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Transaction
import com.wintermute.mobile.cashbutler.presentation.FinancialPanelViewPages

/**
 * Panel view holding all crucial entities to allow the user to browse his financial state for
 * certain root category.
 *
 * @param categories all categories for root category
 * @param accountsByCategoryId accounts sorted by id of categories to which they belong to
 * @param transactionsByAccountId all transactions grouped by accounts
 * @param currentCategory currently selected category browsed by user
 * @param currentAccount currently selected account browsed by user
 * @param onItemClick action to execute when on tap on any item
 * @param onItemDelete actiont to execute
 */
@Composable
fun FinancialPanelView(
    categories: List<FinancialCategory>,
    accountsByCategoryId: Map<Long, List<Account>>,
    transactionsByAccountId: Map<Long, List<Transaction>>,
    currentCategory: Long,
    currentAccount: Long,
    panelView: FinancialPanelViewPages,
    onItemClick: (FinanceDataEntity) -> Unit,
    onItemDelete: (FinanceDataEntity) -> Unit,
) {
    when (panelView) {
        FinancialPanelViewPages.CATEGORIES -> {
            FinancialCategoriesList(
                items = categories,
                onItemClick = {
                    onItemClick(it)
                },

                onItemDelete = {
                    onItemDelete(it)
                }
            )
        }

        FinancialPanelViewPages.ACCOUNTS -> {
            AccountsList(
                items = accountsByCategoryId[currentCategory]!!,
                onItemClick = {
                    onItemClick(it)
                },
                onItemDelete = {
                    onItemDelete(it)
                }
            )
        }

        FinancialPanelViewPages.TRANSACTIONS -> {
            TransactionList(
                items = transactionsByAccountId[currentAccount] ?: emptyList(),
                onItemClick = { onItemClick(it) },
                onItemDelete = { onItemDelete(it) }
            )
        }
    }
}