package com.wintermute.mobile.cashbutler.presentation.viewmodel.components

import androidx.lifecycle.ViewModel
import arrow.core.Either
import arrow.core.Option
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Account
import com.wintermute.mobile.cashbutler.presentation.intent.NewAccountIntent
import com.wintermute.mobile.cashbutler.presentation.view.components.core.model.RadioButtonTextItemModel
import com.wintermute.mobile.cashbutler.presentation.viewmodel.BaseViewModel
import com.wintermute.mobile.cashbutler.presentation.viewmodel.state.components.AccountEditableSheetDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.UUID
import javax.inject.Inject

/**
 * Handles the changes on the state while creating or editing accounts.
 *
 * @author k.kosinski
 */
@HiltViewModel
class AccountEditableSheetDialogViewModel @Inject constructor() : ViewModel(),
    BaseViewModel<NewAccountIntent> {

    private val _state =
        MutableStateFlow<AccountEditableSheetDialogState>(AccountEditableSheetDialogState.Uninitialized)
    val state: StateFlow<AccountEditableSheetDialogState> = _state

    override fun processIntent(intent: NewAccountIntent) {
        when (intent) {
            NewAccountIntent.ResetState -> {
                _state.value = AccountEditableSheetDialogState.Uninitialized
            }

            is NewAccountIntent.InitState -> {
                val viewItems = intent.proposedItems.map {
                    RadioButtonTextItemModel(id = it.id, title = it.title)
                }.toMutableList()
                if (viewItems.isNotEmpty()) {
                    viewItems.add(
                        RadioButtonTextItemModel(id = UUID.randomUUID().toString(), title = "Other")
                    )
                }

                _state.value = AccountEditableSheetDialogState.Initialized(
                    items = intent.proposedItems,
                    viewItems = viewItems,
                    selectedItemTypeIndex = -1,
                    result = Either.Left("Account not found")
                )
            }

            is NewAccountIntent.SelectItemType -> {
                selectItemType(intent.id)
            }

            is NewAccountIntent.UpdateItemsTitle -> {
                _state.value =
                    (_state.value as AccountEditableSheetDialogState.Initialized).copy(accountTitle = intent.title)
            }

            NewAccountIntent.ValidateResult -> {
                checkStateForErrors()
            }
        }
    }

    /**
     * Selects an item type to create an account of this type
     *
     * @param id of target item
     */
    private fun selectItemType(id: String) {
        val state = _state.value as AccountEditableSheetDialogState.Initialized
        val selectedItemIndex = state.viewItems.indexOfFirst { id == it.id }
        val selectedItem = state.viewItems[selectedItemIndex]
        _state.value =
            state.copy(selectedItemTypeIndex = selectedItemIndex, accountType = selectedItem.title)
    }

    /**
     * Before executing confirming action the state will be checked on erros. In this case
     * it will be checkd if the account title is not empty (or blank). If so, an error message will
     * be set and displayed to user.
     *
     */
    private fun checkStateForErrors() {
        val state = _state.value as AccountEditableSheetDialogState.Initialized
        if (state.accountTitle.isNotBlank()) {
            _state.value =
                state.copy(
                    result = Either.Right(Account(title = state.accountTitle)),
                    accountTitleErrorMessage = Option.fromNullable(null)
                )
        } else {
            _state.value = state.copy(
                result = Either.Left("Account invalid"),
                accountTitleErrorMessage = Option.fromNullable("Title must not be empty!")
            )
        }
    }
}