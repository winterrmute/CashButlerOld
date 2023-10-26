package com.wintermute.mobile.cashbutler.presentation.intent

import com.wintermute.mobile.cashbutler.domain.finance.FinancialCategories
import com.wintermute.mobile.cashbutler.presentation.view.components.core.model.CheckBoxTextItemModel

/**
 * Actions which can be performed on default income resources dialog.
 *
 * @author k.kosinski
 */
sealed class DefaultFinancialCategorySelectionIntent : IntentActivity {

    /**
     * Sets the initial data on DefaultFinancialCategorySelectionDialogViewModel
     *
     * @param category parent category of financial categories
     * @param items categories which are currently selected by user for certain parent category
     */
    data class InitState(val category: FinancialCategories, val items: List<String>) :
        DefaultFinancialCategorySelectionIntent()


    /**
     * Destroys the state when no longer needed.
     */
    object DestroyState: DefaultFinancialCategorySelectionIntent()

    /**
     * Toggles financial category selection state.
     *
     * @param item new item with new state.
     */
    data class ToggleItem(val item: CheckBoxTextItemModel) :
        DefaultFinancialCategorySelectionIntent()
}