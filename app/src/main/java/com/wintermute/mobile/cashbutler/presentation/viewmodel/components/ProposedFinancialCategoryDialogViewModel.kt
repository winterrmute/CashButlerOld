package com.wintermute.mobile.cashbutler.presentation.viewmodel.components

import androidx.lifecycle.ViewModel
import arrow.core.Option
import arrow.core.toOption
import com.wintermute.mobile.cashbutler.presentation.intent.ProposedFinancialCategoriesIntent
import com.wintermute.mobile.cashbutler.presentation.viewmodel.BaseViewModel
import com.wintermute.mobile.cashbutler.presentation.viewmodel.state.components.ProposedFinancialCategoryDialogState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class ProposedFinancialCategoryDialogViewModel @Inject constructor(

) : ViewModel(), BaseViewModel<ProposedFinancialCategoriesIntent> {

    private val _state =
        MutableStateFlow<ProposedFinancialCategoryDialogState>(ProposedFinancialCategoryDialogState.Uninitialized)
    val state: StateFlow<ProposedFinancialCategoryDialogState> = _state

    override fun processIntent(intent: ProposedFinancialCategoriesIntent) {
        when (intent) {
            ProposedFinancialCategoriesIntent.ResetState -> {
                _state.value = ProposedFinancialCategoryDialogState.Uninitialized
            }

            is ProposedFinancialCategoriesIntent.InitState -> {
                _state.value = ProposedFinancialCategoryDialogState.Initialized(
                    items = intent.proposedItems, result = Option.fromNullable(null)
                )
            }

            is ProposedFinancialCategoriesIntent.SetResult -> {
                _state.value =
                    (_state.value as ProposedFinancialCategoryDialogState.Initialized).copy(result = intent.result.toOption())
            }
        }
    }
}