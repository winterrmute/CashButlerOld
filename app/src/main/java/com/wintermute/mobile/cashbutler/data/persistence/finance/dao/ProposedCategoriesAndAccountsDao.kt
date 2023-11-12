package com.wintermute.mobile.cashbutler.data.persistence.finance.dao

import androidx.room.Dao
import androidx.room.Query
import com.wintermute.mobile.cashbutler.data.persistence.finance.entity.ProposedCategoriesAndAccounts

/**
 * Access data layer to proposed categories linked to proposed accounts
 * for certain root category (view).
 *
 * @author k.kosinski
 */
@Dao
interface ProposedCategoriesAndAccountsDao {

    /**
     * Selects the proposed categories linked to proposed accounts for certain root category.
     *
     * @param rootCategory target root category for financial categories and accounts
     */
    @Query("SELECT * FROM ProposedCategoriesAndAccounts WHERE rootCategory = :rootCategory")
    fun getProposedCategoriesAndAccounts(rootCategory: String): ProposedCategoriesAndAccounts

}