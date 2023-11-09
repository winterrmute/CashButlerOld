package com.wintermute.mobile.cashbutler.data.persistence.finance.dao

import androidx.room.Dao
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Expense

/**
 * Access layer to expenses information
 */
@Dao
interface ExpensesDao : BaseDao<Expense>