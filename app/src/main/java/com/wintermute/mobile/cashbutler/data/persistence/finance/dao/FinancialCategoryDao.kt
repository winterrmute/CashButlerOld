package com.wintermute.mobile.cashbutler.data.persistence.finance.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.wintermute.mobile.cashbutler.data.persistence.finance.CategoryWithRecords
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialCategory
import kotlinx.coroutines.flow.Flow

/**
 * Financial category access layer.
 *
 * @author k.kosinski
 */
@Dao
interface FinancialCategoryDao {

    /**
     * Persist new category.
     *
     * @param category target category
     */
    @Insert
    fun insert(category: FinancialCategory): Long

    /**
     * Selects financial category by id.
     *
     * @param id of desired category
     */
    @Query("SELECT * FROM FinancialCategories WHERE financial_category_id = :id")
    fun getById(id: Long): FinancialCategory

    /**
     * Selects financial category by id.
     *
     * @param name of desired category
     */
    @Query("SELECT * FROM FinancialCategories WHERE name = :name")
    fun getByName(name: String): FinancialCategory

    @Transaction
    @Query("SELECT * FROM FinancialCategories LEFT JOIN FinancialRecords ON FinancialCategories.financial_category_id = FinancialRecords.category WHERE FinancialCategories.parent = (SELECT financial_category_id FROM FinancialCategories WHERE name = :categoryName);")
    fun getCategoriesWithItems(categoryName: String): Flow<List<CategoryWithRecords>>

    /**
     * Updates a category.
     *
     * @param category target category
     */
    @Update
    fun update(category: FinancialCategory)


    /**
     * Removes a category.
     *
     * @param category target category
     */
    @Delete
    fun delete(category: FinancialCategory)
}