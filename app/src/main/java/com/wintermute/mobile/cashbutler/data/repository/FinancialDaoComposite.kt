package com.wintermute.mobile.cashbutler.data.repository

import com.wintermute.mobile.cashbutler.data.persistence.finance.dao.BaseDao
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.Account
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.FinancialCategory

/**
 * TODO: Refactor me!
 */
data class FinancialDaoComposite(
    val financialCategoryDao: BaseDao<FinancialCategory>,
    val accountDao: BaseDao<Account>,
)