package com.wintermute.mobile.cashbutler.presentation.viewmodel.finance

import androidx.lifecycle.viewModelScope
import com.wintermute.mobile.cashbutler.data.FinancialDataRepository
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialCategory
import com.wintermute.mobile.cashbutler.domain.finance.FinancialCategories
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Handles budget activities made from view.
 *
 * @author k.kosinski
 */
@HiltViewModel
class BudgetViewModel @Inject constructor(
    private val repository: FinancialDataRepository
) : FinancialViewModel(repository, FinancialCategories.BUDGET) {
    lateinit var category: FinancialCategory
    init {
        viewModelScope.launch(Dispatchers.IO) {
            category = repository.getBudgetCategory()
        }
    }
}

