package com.wintermute.mobile.cashbutler.presentation.viewmodel.components

import androidx.lifecycle.ViewModel
import com.wintermute.mobile.cashbutler.domain.finance.DefaultExpenseSources
import com.wintermute.mobile.cashbutler.domain.finance.DefaultIncomeSources
import com.wintermute.mobile.cashbutler.domain.finance.FinancialCategories
import com.wintermute.mobile.cashbutler.presentation.intent.DefaultFinancialCategorySelectionIntent
import com.wintermute.mobile.cashbutler.presentation.view.components.core.model.CheckBoxTextItemModel
import com.wintermute.mobile.cashbutler.presentation.viewmodel.BaseViewModel
import com.wintermute.mobile.cashbutler.presentation.viewmodel.state.components.FinancialCategorySelectionDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * ViewModel handling the state of selection dialog of default financial categories which user can
 * add.
 *
 * @author k.kosinski
 */
@HiltViewModel
class DefaultFinancialCategorySelectionDialogViewModel @Inject constructor(
) : ViewModel(), BaseViewModel<DefaultFinancialCategorySelectionIntent> {

    private var _state = MutableStateFlow<FinancialCategorySelectionDialogState>(
        FinancialCategorySelectionDialogState.Uninitialized
    )
    val state: StateFlow<FinancialCategorySelectionDialogState> = _state

    override fun processIntent(intent: DefaultFinancialCategorySelectionIntent) {
        when (intent) {
            is DefaultFinancialCategorySelectionIntent.ToggleItem -> {
                _state.value = (_state.value as FinancialCategorySelectionDialogState.Initialized)
                    .let { state ->
                        state.copy(
                            itemSelectionState = state.itemSelectionState.toMutableList().map {
                                if (it.title == intent.item.title) intent.item else it
                            }
                        )
                    }
            }

            is DefaultFinancialCategorySelectionIntent.InitState -> {
                val availableItems = when (intent.category) {
                    FinancialCategories.BUDGET -> enumValues<DefaultIncomeSources>().map { it.displayName }
                        .toList()

                    FinancialCategories.EXPENSES -> enumValues<DefaultExpenseSources>().map { it.displayName }
                        .toList()
                }

                val selectedItems = prepareSelectedState(availableItems, intent.items)

                _state.value = FinancialCategorySelectionDialogState.Initialized(
                    itemSelectionState = selectedItems,
                    defaultItems = availableItems
                )
            }

            DefaultFinancialCategorySelectionIntent.DestroyState -> _state.value =
                FinancialCategorySelectionDialogState.Uninitialized
        }
    }

    private fun prepareSelectedState(
        availableItems: List<String>,
        selectedItems: List<String>
    ): List<CheckBoxTextItemModel> {
        return availableItems.map {
            CheckBoxTextItemModel(it, selectedItems.any { item -> it == item })
        }
    }
}