package com.wintermute.mobile.cashbutler.presentation.view.components.finance

import androidx.compose.runtime.Composable
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Account

/**
 * Component holding all accounts by certain category.
 *
 * @param items accounts list for certain category
 * @param onItemClick action to execute on tap on account element
 * @param onItemDelete action to execute when an item is deleted
 */
@Composable
fun AccountsList(
    items: List<Account>,
    onItemClick: (Account) -> Unit,
    onItemDelete: (Account) -> Unit
) {
    items.forEach {
        FinancialCategoryCard(
            title = it.title,
            balance = it.balance.toString(),
            onClick = {
                onItemClick(it)
            },
            onDeleteClick = {
                onItemDelete(it)
            }
        )
    }
}