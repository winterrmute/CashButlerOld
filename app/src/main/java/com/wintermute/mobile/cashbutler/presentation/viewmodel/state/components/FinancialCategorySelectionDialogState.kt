package com.wintermute.mobile.cashbutler.presentation.viewmodel.state.components

import com.wintermute.mobile.cashbutler.presentation.view.components.core.model.CheckBoxTextItemModel

/**
 * State of the default financial categories selection.
 *
 * @author k.kosinski
 */
sealed class FinancialCategorySelectionDialogState {
    object Uninitialized : FinancialCategorySelectionDialogState()

    /**
     * Initialized state
     *
     * @param itemSelectionState currently selected financial categories by user
     * @param defaultItems items that are by default to select
     */
    data class Initialized(
        val itemSelectionState: List<CheckBoxTextItemModel>,
        val defaultItems: List<String>
    ): FinancialCategorySelectionDialogState()

    data class Error(val errorMessage: String) : FinancialCategorySelectionDialogState()
}