package com.wintermute.mobile.cashbutler.data.persistence.finance.dao

import androidx.room.Dao
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Saving

/**
 * Access layer to savings information
 *
 * @author k.kosinski
 */
@Dao
interface SavingsDao : BaseDao<Saving>