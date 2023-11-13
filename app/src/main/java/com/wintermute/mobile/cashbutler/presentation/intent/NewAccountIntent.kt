package com.wintermute.mobile.cashbutler.presentation.intent

import com.wintermute.mobile.cashbutler.data.persistence.finance.composite.ProposedAccount

/**
 * Actions that has to be handled on creating or editing accounts
 *
 * @author k.kosinski
 */
sealed class NewAccountIntent : IntentActivity {

    /**
     * Initializes state
     *
     * @param proposedItems accounts which are proposed to certain category. May be empty
     */
    data class InitState(
        val proposedItems: List<ProposedAccount>,
    ) : NewAccountIntent()

    /**
     * Select Item type from current elements by id
     *
     * @param id unique identifier
     */
    data class SelectItemType(val id: String) : NewAccountIntent()

    /**
     * Update title of account
     *
     * @param title to set on account
     */
    data class UpdateItemsTitle(val title: String) : NewAccountIntent()

    /**
     * validate the creation of account
     */
    object ValidateResult: NewAccountIntent()

    /**
     * resets state
     */
    object ResetState : NewAccountIntent()
}