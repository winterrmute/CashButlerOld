package com.wintermute.mobile.cashbutler.presentation.view.finance

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.navigation.compose.hiltViewModel
import arrow.core.Option
import arrow.core.toOption
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Account
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Transaction
import com.wintermute.mobile.cashbutler.domain.finance.FinancialCategories
import com.wintermute.mobile.cashbutler.presentation.FinancialPanelViewPages
import com.wintermute.mobile.cashbutler.presentation.intent.FinancialActionIntent
import com.wintermute.mobile.cashbutler.presentation.view.components.core.HeaderNavigationElement
import com.wintermute.mobile.cashbutler.presentation.view.components.finance.FinancialPanelView
import com.wintermute.mobile.cashbutler.presentation.view.components.finance.forms.AccountsEditableForm
import com.wintermute.mobile.cashbutler.presentation.view.components.finance.forms.FinancialCategoryEditableForm
import com.wintermute.mobile.cashbutler.presentation.view.components.finance.forms.TransactionEditableSheetForm
import com.wintermute.mobile.cashbutler.presentation.viewmodel.finance.FinancialDataViewModel
import com.wintermute.mobile.cashbutler.presentation.viewmodel.state.finance.FinancialState

/**
 * Main view showing the categories for cash flow. The categories are passed into this composable
 * incl. its accounts and their transactions.
 *
 * @param vm viewModel responsible for managing the state of this composable and handling the user actions.
 */
@Composable
fun CashFlowView(
    vm: FinancialDataViewModel = hiltViewModel()
) {
    val vmState = vm.state.collectAsState()

    when (val state = vmState.value) {
        is FinancialState.Initialized -> {
            var currentPanelPage by remember { mutableStateOf(FinancialPanelViewPages.CATEGORIES) }
            var showAddDialog by remember { mutableStateOf(false) }

            var currentCategory by remember { mutableStateOf(FinancialCategory(title = "")) }
            var currentAccount by remember { mutableStateOf(Account(title = "", categoryId = 0L)) }
            var currentTransaction by remember {
                mutableStateOf<Option<Transaction>>(
                    Option.fromNullable(
                        null
                    )
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(245, 245, 245))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    HeaderNavigationElement(
                        title = FinancialCategories.CASH_FLOW.displayName,
                        subtitle = currentPanelPage.displayName,
                        onAddClick = {
                            showAddDialog = true
                        }
                    )

                    FinancialPanelView(
                        categories = state.categories,
                        accountsByCategoryId = state.accounts,
                        transactionsByAccountId = state.transactions,
                        currentCategory = currentCategory,
                        currentAccount = currentAccount,
                        panelView = currentPanelPage,
                        onItemClick = {
                            when (it) {
                                is FinancialCategory -> {
                                    currentCategory = it
                                    currentPanelPage = FinancialPanelViewPages.ACCOUNTS
                                }

                                is Account -> {
                                    currentAccount = it
                                    currentPanelPage = FinancialPanelViewPages.TRANSACTIONS
                                }

                                is Transaction -> {
                                    currentTransaction = it.toOption()
                                    showAddDialog = true
                                }
                            }
                        },
                        onItemDelete = {
                            vm.processIntent(FinancialActionIntent.RemoveItem(it))
                        }
                    )
                }
            }

            if (showAddDialog) {
                when (currentPanelPage) {
                    FinancialPanelViewPages.CATEGORIES -> {
                        FinancialCategoryEditableForm(
                            proposedItems = state.proposedItems.keys.toList(),
                            ownedItems = state.categories.map { it.title },
                            onConfirm = {
                                it.forEach { newCategory ->
                                    vm.processIntent(
                                        FinancialActionIntent.AddItem(
                                            FinancialCategory(
                                                title = newCategory
                                            )
                                        )
                                    )
                                }
                            },
                            onDismiss = { showAddDialog = false }
                        )
                    }

                    FinancialPanelViewPages.ACCOUNTS -> {
                        AccountsEditableForm(
                            proposedItems = state.proposedItems[currentCategory.title]?.accounts
                                ?: emptyList(),
                            onConfirm = { account ->
                                account.fold(
                                    ifLeft = {
                                        //TODO: handle error
                                    },
                                    ifRight = {
                                        val result =
                                            it.copy(categoryId = currentCategory.id)
                                        vm.processIntent(
                                            FinancialActionIntent.AddItem(
                                                result
                                            )
                                        )
                                    }
                                )
                            },
                            onDismiss = { showAddDialog = false }
                        )
                    }

                    FinancialPanelViewPages.TRANSACTIONS -> {
                        TransactionEditableSheetForm(
                            transaction = currentTransaction,
                            onConfirm = {
                                val transaction =
                                    it.copy(accountId = currentAccount.id)
                                if (transaction.id > 0) {
                                    vm.processIntent(
                                        FinancialActionIntent.UpdateItem(
                                            transaction
                                        )
                                    )
                                } else {
                                    vm.processIntent(
                                        FinancialActionIntent.AddItem(
                                            transaction
                                        )
                                    )
                                }
                                currentTransaction = Option.fromNullable(null)
                                showAddDialog = false
                            },
                            onDismiss = {
                                currentTransaction = Option.fromNullable(null)
                                showAddDialog = false
                            }
                        )
                    }
                }
            }

            BackHandler(showAddDialog || currentPanelPage.ordinal > 0) {
                if (showAddDialog) {
                    showAddDialog = false
                } else {
                    currentPanelPage =
                        enumValues<FinancialPanelViewPages>()[currentPanelPage.ordinal - 1]
                }
            }
        }

        is FinancialState.Error -> {

        }

        FinancialState.Uninitialized -> {
            vm.processIntent(FinancialActionIntent.InitState(FinancialCategories.CASH_FLOW))
        }
    }
}