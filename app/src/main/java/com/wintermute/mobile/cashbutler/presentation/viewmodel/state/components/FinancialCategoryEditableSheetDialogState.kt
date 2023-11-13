package com.wintermute.mobile.cashbutler.presentation.viewmodel.state.components

import com.wintermute.mobile.cashbutler.presentation.view.components.core.model.CheckBoxTextItemModel

/**
 * State for dialog sheet handling the process of adding new categories to root category
 *
 * @author k.kosinski
 */
sealed class FinancialCategoryEditableSheetDialogState {

    /**
     * Empty state
     */
    object Uninitialized : FinancialCategoryEditableSheetDialogState()

    /**
     * Initialized state
     *
     * @param items proposed to the user from which a category can be selected
     * @param result list of items selected by user which should be created on confirm
     *
     */
    data class Initialized(
        val items: List<CheckBoxTextItemModel>,
        val result: List<CheckBoxTextItemModel> = emptyList()
    ) : FinancialCategoryEditableSheetDialogState()

    /**
     * Error state
     *
     * @param errorMessage containing information what went wrong
     */
    data class Error(val errorMessage: String) : FinancialCategoryEditableSheetDialogState()
}