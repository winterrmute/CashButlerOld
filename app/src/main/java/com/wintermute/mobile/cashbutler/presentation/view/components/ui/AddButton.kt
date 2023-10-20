package com.wintermute.mobile.cashbutler.presentation.view.components.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.wintermute.mobile.cashbutler.R
import com.wintermute.mobile.cashbutler.presentation.view.components.core.CustomizableButton
import com.wintermute.mobile.cashbutler.ui.theme.appBlue
import dagger.hilt.android.qualifiers.ApplicationContext

@Composable
fun AddButton(
    @ApplicationContext context: Context = LocalContext.current.applicationContext,
    onClick: () -> Unit
) {
    CustomizableButton(
        buttonColor = appBlue,
        value = context.getString(R.string.button_add)
    ) {
        onClick()
    }
}