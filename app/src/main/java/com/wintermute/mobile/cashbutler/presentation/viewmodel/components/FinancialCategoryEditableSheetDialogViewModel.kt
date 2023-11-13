package com.wintermute.mobile.cashbutler.presentation.viewmodel.components

import androidx.lifecycle.ViewModel
import arrow.core.Option
import com.wintermute.mobile.cashbutler.presentation.intent.NewFinancialCategoriesIntent
import com.wintermute.mobile.cashbutler.presentation.view.components.core.model.CheckBoxTextItemModel
import com.wintermute.mobile.cashbutler.presentation.viewmodel.BaseViewModel
import com.wintermute.mobile.cashbutler.presentation.viewmodel.state.components.FinancialCategoryEditableSheetDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Handles the actions coming from view and manages the state while creating the categories
 *
 * @author k.kosinski
 */
@HiltViewModel
class FinancialCategoryEditableSheetDialogViewModel @Inject constructor(

) : ViewModel(), BaseViewModel<NewFinancialCategoriesIntent> {

    private val _state =
        MutableStateFlow<FinancialCategoryEditableSheetDialogState>(
            FinancialCategoryEditableSheetDialogState.Uninitialized
        )
    val state: StateFlow<FinancialCategoryEditableSheetDialogState> = _state

    override fun processIntent(intent: NewFinancialCategoriesIntent) {
        when (intent) {
            NewFinancialCategoriesIntent.ResetState -> {
                _state.value = FinancialCategoryEditableSheetDialogState.Uninitialized
            }

            is NewFinancialCategoriesIntent.InitState -> {
                initializeState(
                    proposedItems = intent.proposedItems,
                    ownedItems = intent.ownedItems
                )
            }

            is NewFinancialCategoriesIntent.ToggleItem -> {
                val state = _state.value as FinancialCategoryEditableSheetDialogState.Initialized
                handleChangingItemState(intent.item, state.result)
            }

            is NewFinancialCategoriesIntent.AddCustomItem -> {
                addCustomItemToResult(intent.id, intent.title)
            }

            is NewFinancialCategoriesIntent.ModifyCustomItemTitle -> {
                modifyCustomItem(intent.id, intent.newTitle)
            }

            is NewFinancialCategoriesIntent.RemoveCustomItem -> {
                removeCustomItem(intent.id)
            }

            NewFinancialCategoriesIntent.ValidateResult -> {
                validateResult()
            }
        }
    }

    /**
     * Checks if the custom item's title is empty and if yes, provides an error message to the user.
     */
    private fun validateResult() {
        val state = _state.value as FinancialCategoryEditableSheetDialogState.Initialized
        val result = state.result.filter { it.title.isBlank() }.toList()

        val errorMessage: String? = if (result.isEmpty()) {
            null
        } else {
            "Title of this item may not be empty"
        }

        _state.value = state.copy(
            customItemErrorMessage = Option.fromNullable(errorMessage)
        )
    }

    /**
     * Localizes in results the item which should be removed and removes it from current result.
     *
     * @param id to identify the target
     */
    private fun removeCustomItem(id: String) {
        val state = _state.value as FinancialCategoryEditableSheetDialogState.Initialized
        val modifiedResult = handleToggledItem(
            CheckBoxTextItemModel(id, "", isChecked = false, isEnabled = false), state.result
        )
        _state.value = state.copy(result = modifiedResult)
    }

    /**
     * Localizes a custom item and modifies its title.
     *
     * @param id unique identifier to localize the item
     * @param title new text to be set as title
     */
    private fun modifyCustomItem(id: String, title: String) {
        val state = _state.value as FinancialCategoryEditableSheetDialogState.Initialized
        var errMessage = state.customItemErrorMessage
        val index = state.result.indexOfFirst { it.id == id }
        val modifiedItem = state.result[index].copy(title = title)

        if (modifiedItem.title.isNotBlank() && state.customItemErrorMessage.isSome()){
            errMessage = Option.fromNullable(null)
        }

        val modifiedItemList = state.result.toMutableList()
        modifiedItemList[index] = modifiedItem
        _state.value = state.copy(result = modifiedItemList, customItemErrorMessage = errMessage)
    }

    /**
     * Prepares the state of proposed and owned items. If any items from the proposed items
     * are owned by the user, they are disabled so they can't be recreated.
     *
     * @param proposedItems items which user may choose
     * @param ownedItems items which user already has created
     */
    private fun initializeState(proposedItems: List<String>, ownedItems: List<String>) {
        val items = proposedItems.map {
            val isChecked = ownedItems.any { owned -> it == owned }
            //If the item is owned, the user can't change it's state.
            val isEnabled = !isChecked
            CheckBoxTextItemModel(
                title = it,
                isChecked = isChecked,
                isEnabled = isEnabled
            )
        }
        _state.value = FinancialCategoryEditableSheetDialogState.Initialized(items = items)
    }

    /**
     * Creates a new item and adds it to result's set.
     *
     * @param id to set on the new item
     * @param title its text
     */
    private fun addCustomItemToResult(id: String, title: String) {
        //if it's being created then it has to be checked and enabled
        val newItem = CheckBoxTextItemModel(
            id = id,
            title = title,
            isChecked = true,
            isEnabled = true
        )

        val state = _state.value as FinancialCategoryEditableSheetDialogState.Initialized
        val newResult = state.result.filter { it.isChecked }.toMutableList()
        newResult.add(newItem)
        _state.value = state.copy(result = newResult)
    }


    /**
     * Reacts to changing the state of proposed (or custom) items. When it's state is changed to
     * checked the item is added to result's set, otherwise it's removed.
     *
     * @param intent
     */
    private fun handleChangingItemState(
        item: CheckBoxTextItemModel,
        currentResult: List<CheckBoxTextItemModel>
    ) {
        val modifiedResult = handleToggledItem(item, currentResult)
        _state.value =
            (_state.value as FinancialCategoryEditableSheetDialogState.Initialized).copy(result = modifiedResult)
    }

    private fun handleToggledItem(
        item: CheckBoxTextItemModel,
        currentResult: List<CheckBoxTextItemModel>
    ): List<CheckBoxTextItemModel> =
        currentResult.toMutableList().apply {
            if (item.isChecked) add(item) else removeIf { it.id == item.id }
        }
}