package com.wintermute.mobile.cashbutler.presentation.viewmodel.finance

import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord
import com.wintermute.mobile.cashbutler.presentation.intent.FinancialRecordIntent
import kotlinx.coroutines.flow.Flow

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

    fun removeRecord(intent: FinancialRecordIntent.RemoveRecord)
}