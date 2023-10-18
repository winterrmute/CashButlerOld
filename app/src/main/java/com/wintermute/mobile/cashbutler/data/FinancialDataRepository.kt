package com.wintermute.mobile.cashbutler.data

import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.dao.FinancialCategoryDao
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord
import com.wintermute.mobile.cashbutler.data.persistence.finance.dao.FinancialRecordDao
import com.wintermute.mobile.cashbutler.domain.finance.FinancialCategories
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FinancialDataRepository @Inject constructor(
    private val categoryDao: FinancialCategoryDao,
    private val recordDao: FinancialRecordDao
) {

    fun getFinancialData(category: FinancialCategories): Flow<Map<FinancialCategory, List<FinancialRecord>>> {
        return categoryDao.getCategoriesWithItems(category.displayName).map {
            it.associate { categoryWithRecords ->
                categoryWithRecords.category to categoryWithRecords.records
            }
        }
    }

    fun storeCategory(category: FinancialCategory): Long {
        return categoryDao.insert(category)
    }

    fun storeRecord(record: FinancialRecord): Long {
        return recordDao.insert(record)
    }

    fun getBudgetCategory(): FinancialCategory {
        return categoryDao.getByName(FinancialCategories.BUDGET.displayName)
    }

    fun removeCategory(category: FinancialCategory){
        categoryDao.delete(category)
    }

    fun removeRecord(record: FinancialRecord) {
        return recordDao.delete(record);
    }

    fun getExpenseCategory(): FinancialCategory {
        return categoryDao.getByName(FinancialCategories.EXPENSES.displayName)
    }
}