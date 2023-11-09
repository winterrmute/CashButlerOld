package com.wintermute.mobile.cashbutler.data.persistence.finance.dao

import androidx.room.Dao
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Account

/**
 * Dao for accounts with default operations
 *
 * @author k.kosinski
 */
@Dao
interface AccountDao : BaseDao<Account>