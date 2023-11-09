package com.wintermute.mobile.cashbutler.data.persistence.finance.dao

import androidx.room.Dao
import androidx.room.Query
import com.wintermute.mobile.cashbutler.data.persistence.finance.composite.CashFlowWithAccountsByCategory
import kotlinx.coroutines.flow.Flow

/**
 * Access layer to cash flow information
 *
 * @author k.kosinski
 */
@Dao
interface CashFlowDao : FinanceDataCompositeDao {
    /**
     * @return list of cash flow categories, it's accounts and related transactions
     */
    @androidx.room.Transaction
    @Query("SELECT * FROM CashFlow")
    fun getAll(): Flow<List<CashFlowWithAccountsByCategory>>
}