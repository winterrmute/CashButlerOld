package com.wintermute.mobile.cashbutler.data.persistence.finance.dao

import androidx.room.Dao
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinancialCategory

/**
 * Dao for categories with default operations
 *
 * @author k.kosinski
 */
@Dao
interface FinancialCategoryDao : BaseDao<FinancialCategory>