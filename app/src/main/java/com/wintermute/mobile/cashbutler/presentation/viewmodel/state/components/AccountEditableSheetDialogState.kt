package com.wintermute.mobile.cashbutler.presentation.viewmodel.state.components

import arrow.core.Either
import arrow.core.Option
import com.wintermute.mobile.cashbutler.data.persistence.finance.composite.ProposedAccount
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Account
import com.wintermute.mobile.cashbutler.presentation.view.components.core.model.RadioButtonTextItemModel

/**
 * State of the editable sheet for creating or editing accounts.
 *
 * @author k.kosinski
 */
sealed class AccountEditableSheetDialogState {
    /**
     * Empty state
     */
    object Uninitialized : AccountEditableSheetDialogState()

    /**
     * Initialized state
     *
     * @param items proposed accounts to certain category. May be empty
     * @param selectedItemTypeIndex account type from proposed items (or custom type)
     * @param accountTitle title of this account
     * @param accountTitleErrorMessage error message in case the state is invalid for creation
     * @param accountType todo: update me
     * @param result result ready to create
     */
    data class Initialized(
        val items: List<ProposedAccount>,
        val viewItems: List<RadioButtonTextItemModel>,
        val selectedItemTypeIndex: Int,
        val accountTitle: String = "",
        val accountTitleErrorMessage: Option<String> = Option.fromNullable(null),
        val accountType: String = "New Account",
        val result: Either<String, Account>,
    ) : AccountEditableSheetDialogState()

    /**
     * Error state
     *
     * @param errorMessage in case something went wrong
     */
    data class Error(val errorMessage: String) : AccountEditableSheetDialogState()
}