package com.wintermute.mobile.cashbutler.presentation.intent

import com.wintermute.mobile.cashbutler.presentation.view.components.core.model.CheckBoxTextItemModel

/**
 * Action which can be taken and produces changes to business logic while creating new category
 *
 * @author k.kosinski
 */
sealed class NewFinancialCategoriesIntent : IntentActivity {

    /**
     * Initializes state
     *
     * @param proposedItems categories which are proposed to user
     * @param ownedItems proposed items which are already owned by user
     */
    data class InitState(
        val proposedItems: List<String>,
        val ownedItems: List<String>
    ) : NewFinancialCategoriesIntent()

    /**
     * Change state of an item which may be added. Don't affect currently owned items as they are disabled until they are deleted.
     *
     * @param item to change its state. When the item is checked it will be added to result
     *          otherwise it will be removed from result.
     */
    data class ToggleItem(val item: CheckBoxTextItemModel) :
        NewFinancialCategoriesIntent()

    /**
     * Action for handling custom item which is not in proposed items.
     *
     * @param id to set as identifier
     * @param title of newly created item
     */
//    data class AddCustomItem(val item: CheckBoxTextItemModel) : NewFinancialCategoriesIntent()
    data class AddCustomItem(val id: String, val title: String) : NewFinancialCategoriesIntent()

    /**
     * Action which makes it possible to edit an item which has been created by user
     *
     * @param id unique identifier to identify the target in result's list
     * @param newTitle new title which should be set on the new item
     */
    data class ModifyCustomItemTitle(val id: String, val newTitle: String) : NewFinancialCategoriesIntent()

    /**
     * Action which delegates deleting item from result
     *
     * @param id unique identifier to identify the target in result's list
     */
    data class RemoveCustomItem(val id: String) : NewFinancialCategoriesIntent()

    object ResetState : NewFinancialCategoriesIntent()
}