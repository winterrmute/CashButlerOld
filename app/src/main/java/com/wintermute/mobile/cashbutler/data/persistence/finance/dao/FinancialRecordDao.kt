package com.wintermute.mobile.cashbutler.data.persistence.finance.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord
import kotlinx.coroutines.flow.Flow

/**
 * Financial record access layer.
 *
 * @author k.kosinski
 */
@Dao
interface FinancialRecordDao {
    /**
     * Persist new category.
     *
     * @param category target record
     */
    @Insert
    fun insert(category: FinancialRecord): Long

    /**
     * Selects financial category by id.
     *
     * @param id of desired record
     */
    @Query("SELECT * FROM FinancialRecords WHERE financial_record_id = :id")
    fun getById(id: Long): FinancialRecord

    /**
     * Selects financial category by id.
     *
     * @param title of desired record
     */
    @Query("SELECT * FROM FinancialRecords WHERE title = :title")
    fun getByName(title: String): FinancialRecord

    @Transaction
    @Query("SELECT c.*, r.* FROM FinancialCategories c LEFT JOIN FinancialRecords r ON c.financial_category_id = r.category")
    fun getFinancialRecords(): Flow<Map<FinancialCategory, List<FinancialRecord>>>


    /**
     * Updates a category.
     *
     * @param category target record
     */
    @Update
    suspend fun update(category: FinancialRecord)


    /**
     * Removes a category.
     *
     * @param category target record
     */
    @Delete
    suspend fun delete(category: FinancialRecord)
}