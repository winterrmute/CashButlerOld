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
     * @param category target category
     */
    @Insert
    fun insert(category: FinancialRecordEntity): Long

    /**
     * Selects financial category by id.
     *
     * @param id of desired category
     */
    @Query("SELECT id, name, parent FROM FinancialCategories WHERE id = :id")
    fun getById(id: Long): FinancialRecordEntity

    /**
     * Selects financial category by id.
     *
     * @param name of desired category
     */
    @Query("SELECT id, name, parent FROM FinancialCategories WHERE name = :name")
    fun getByName(name: String)

    /**
     * Updates a category.
     *
     * @param category target category
     */
    @Update
    fun update(category: FinancialRecordEntity)


    /**
     * Removes a category.
     *
     * @param category target category
     */
    @Delete
    fun delete(category: FinancialRecordEntity)
}