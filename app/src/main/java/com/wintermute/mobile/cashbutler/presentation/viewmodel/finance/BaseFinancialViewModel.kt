package com.wintermute.mobile.cashbutler.presentation.viewmodel.finance

import com.wintermute.mobile.cashbutler.presentation.intent.FinancialActionIntent
import com.wintermute.mobile.cashbutler.presentation.viewmodel.BaseViewModel

/**
 * Defines base view model handling financial activities.
 *
 * @author k.kosinski
 */
interface BaseFinancialViewModel : BaseViewModel<FinancialActionIntent> {
    /**
     * Retrieves data from source
     */
    fun retrieveData()

    /**
     * Adding financial category for holding records related to it.
     *
     * @param intent: action that should be handled
     */
    fun addCategory(intent: FinancialActionIntent.AddFinanceCategory)

    /**
     * Removing financial category with all related records.
     *
     * @param intent: action that should be handled
     */
    fun removeCategory(intent: FinancialActionIntent.RemoveCategory)

    /**
     * Adding financial record.
     *
     * @param intent: action that should be handled
     */
    fun addRecord(intent: FinancialActionIntent.AddRecord)

    /**
     * Updating financial record.
     *
     * @param intent: action that should be handled
     */
    fun updateRecord(intent: FinancialActionIntent.UpdateRecord)

    /**
     * Removing financial record.
     *
     * @param intent: action that should be handled
     */
    fun removeRecord(intent: FinancialActionIntent.RemoveRecord)
}