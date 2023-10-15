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
 * Handles expenses activities made from view.
 *
 * @author k.kosinski
 */
@HiltViewModel
class ExpensesViewModel @Inject constructor(
    private val repository: FinancialDataRepository
) : FinancialViewModel(repository, FinancialCategories.EXPENSES){

    lateinit var category: FinancialCategory
    init {
        viewModelScope.launch(Dispatchers.IO) {
            category = repository.getExpenseCategory()
        }
    }

}