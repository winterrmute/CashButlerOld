package com.wintermute.mobile.cashbutler.data.persistence.finance

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

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
    fun insert(category: FinancialCategoryEntity): Long

    /**
     * Selects financial category by id.
     *
     * @param id of desired category
     */
    @Query("SELECT id, name, parent FROM FinancialCategories WHERE id = :id")
    fun getById(id: Long): FinancialCategoryEntity

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
    fun update(category: FinancialCategoryEntity)


    /**
     * Removes a category.
     *
     * @param category target category
     */
    @Delete
    fun delete(category: FinancialCategoryEntity)
}