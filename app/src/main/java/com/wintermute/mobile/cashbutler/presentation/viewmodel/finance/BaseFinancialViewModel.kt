package com.wintermute.mobile.cashbutler.presentation.viewmodel.finance

import com.wintermute.mobile.cashbutler.presentation.intent.FinancialRecordIntent

/**
 * Defines base view model handling financial activities.
 *
 * @author k.kosinski
 */
interface BaseFinancialViewModel {
    fun retrieveData()

    fun processIntent(intent: FinancialRecordIntent)

    fun addCategory(intent: FinancialRecordIntent.AddFinanceCategory)

    fun removeCategory(intent: FinancialRecordIntent.RemoveCategory)

    fun addRecord(intent: FinancialRecordIntent.AddRecord)

    fun updateRecord(intent: FinancialRecordIntent.UpdateRecord)

    fun removeRecord(intent: FinancialRecordIntent.RemoveRecord)
}