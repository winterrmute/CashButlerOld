package com.wintermute.mobile.cashbutler.presentation.view.components.finance

import androidx.compose.runtime.Composable
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Transaction

@Composable
fun TransactionList(
    items: List<Transaction>,
    onItemClick: (Transaction) -> Unit,
    onItemDelete: (Transaction) -> Unit
) {
    items.forEach {
        FinancialTransactionCard(
            title = it.title,
            amount = it.amount,
            onItemClick = {
                onItemClick(it)
            },
            onItemDelete = {
                onItemDelete(it)
            })
    }
}