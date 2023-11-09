package com.wintermute.mobile.cashbutler.data.persistence.finance.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Update
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinanceDataEntity

/**
 * TODO: Refactor me!
 */
interface BaseDao<T : FinanceDataEntity> {
    /**
     * Creates a new entry of the entity from T.
     *
     * @param target entity from T
     *
     * @return id of newly created element
     */
    @Insert
    fun insert(target: T): Long

    /**
     * Updates entry of the entity from T.
     *
     * @param target entity from T
     */
    @Update
    fun update(target: T)

    /**
     * Removes entry of the entity from T.
     *
     * @param target entity from T
     */
    @Delete
    fun delete(target: T)
}