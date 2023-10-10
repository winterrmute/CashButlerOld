package com.wintermute.mobile.cashbutler.data.persistence.finance

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

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
    fun insert(category: FinancialRecordEntity): Long

    /**
     * Selects financial category by id.
     *
     * @param id of desired record
     */
    @Query("SELECT id, title, amount, category FROM FinancialRecords WHERE id = :id")
    fun getById(id: Long): FinancialRecordEntity

    /**
     * Selects financial category by id.
     *
     * @param title of desired record
     */
    @Query("SELECT id, title, amount, category FROM FinancialRecords WHERE title = :title")
    fun getByName(title: String): FinancialRecordEntity

    /**
     * Updates a category.
     *
     * @param category target record
     */
    @Update
    fun update(category: FinancialRecordEntity)


    /**
     * Removes a category.
     *
     * @param category target record
     */
    @Delete
    fun delete(category: FinancialRecordEntity)
}