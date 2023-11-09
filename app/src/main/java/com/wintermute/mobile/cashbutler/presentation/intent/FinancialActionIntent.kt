package com.wintermute.mobile.cashbutler.presentation.intent

import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Account
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinanceDataEntity
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Transaction
import com.wintermute.mobile.cashbutler.domain.finance.FinancialCategories


/**
 * Defines user actions which can be dispatched to update the logic state.
 *
 * @author k.kosinski
 */
sealed class FinancialActionIntent : IntentActivity {

    /**
     * Sets the key parameter to initialize the state correctly.
     *
     * @param category of data which should be get for certain view
     */
    data class InitState(val category: FinancialCategories) : FinancialActionIntent()

    /**
     * Clears the data in state.
     */
    object DestroyState : FinancialActionIntent()

    /**
     * Intent to add an item of certain type
     *
     * @param item one of possible items: category, account or transaction
     */
    data class AddItem(val item: FinanceDataEntity) : FinancialActionIntent()

    /**
     * Intent to remove an item of certain type
     *
     * @param item one of possible items: category, account or transaction
     */
    data class RemoveItem(val item: FinanceDataEntity) : FinancialActionIntent()

    /**
     * Intent to update an item of certain type
     *
     * @param item one of possible items: category, account or transaction
     */
    data class UpdateItem(val item: FinanceDataEntity) : FinancialActionIntent()
}