package com.wintermute.mobile.cashbutler.presentation.viewmodel

import com.wintermute.mobile.cashbutler.presentation.intent.IntentActivity

/**
 * Contract for any view model
 *
 * @author k.kosinski
 */
interface BaseViewModel<T : IntentActivity> {

    /**
     * Takes an Intent from IntentActivity and processes it.
     *
     * @param intent action to be processed.
     */
    fun processIntent(intent: T)
}
