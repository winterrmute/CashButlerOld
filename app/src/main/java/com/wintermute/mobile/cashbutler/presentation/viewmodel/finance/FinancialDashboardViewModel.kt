package com.wintermute.mobile.cashbutler.presentation.viewmodel.finance

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.wintermute.mobile.cashbutler.data.persistence.finance.CategoryWithRecords
import com.wintermute.mobile.cashbutler.presentation.viewmodel.state.finance.FinancialDashboardState
import com.wintermute.mobile.cashbutler.presentation.viewmodel.state.finance.FinancialDataState
import com.wintermute.mobile.cashbutler.presentation.view.components.reporting.DonutChartData
import com.wintermute.mobile.cashbutler.presentation.view.components.reporting.DonutChartDataCollection
import com.wintermute.mobile.cashbutler.ui.theme.charts.ChartColors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FinancialDashboardViewModel @Inject constructor(
    private val budgetViewModel: BudgetViewModel,
    private val expensesViewModel: ExpensesViewModel
) : ViewModel() {
    private val _state =
        MutableStateFlow<FinancialDashboardState>(FinancialDashboardState.Uninitialized).apply {
            handleInitializedState()
        }
    val state: StateFlow<FinancialDashboardState> = _state

    init {
        viewModelScope.launch {
            combine(budgetViewModel.state, expensesViewModel.state) { budgetState, expensesState ->
                Pair(budgetState, expensesState)
            }.collect { (budgetState, expensesState) ->
                if (budgetState is FinancialDataState.Initialized && expensesState is FinancialDataState.Initialized) {
                    handleInitializedState()
                }
            }
        }
    }

    private fun handleInitializedState() {
        val budgetState = budgetViewModel.state.value
        val expensesState = expensesViewModel.state.value

        if (budgetState is FinancialDataState.Initialized && expensesState is FinancialDataState.Initialized) {
            val budgetChartData = prepareChartData(budgetState.financialRecords)
            val expensesChartData = prepareChartData(expensesState.financialRecords)
            _state.value = FinancialDashboardState.Initialized(
                budget = budgetState.balance,
                expenses = expensesState.balance,
                balance = budgetState.balance.subtract(expensesState.balance),
                budgetData = DonutChartDataCollection(budgetChartData),
                expensesData = DonutChartDataCollection(expensesChartData),
                balanceData = DonutChartDataCollection(budgetChartData + expensesChartData)
            )
        } else {
            // Wait for both ViewModels to be initialized
        }
    }

    private fun prepareChartData(data: List<CategoryWithRecords>): List<DonutChartData>{
        return data.mapIndexed { index, item ->
            DonutChartData(item.category.balance.toFloat(), color = ChartColors.getByIndex(index), title = item.category.name)
        }
    }
}