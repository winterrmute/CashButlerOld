package com.wintermute.mobile.cashbutler.presentation.view.components.ui

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.wintermute.mobile.cashbutler.R
import com.wintermute.mobile.cashbutler.presentation.view.components.core.CustomizableButton
import dagger.hilt.android.qualifiers.ApplicationContext

@Composable
fun CancelButton(
    @ApplicationContext context: Context = LocalContext.current.applicationContext,
    onClick: () -> Unit
) {
    CustomizableButton(
        buttonColor = Color.DarkGray,
        value = context.getString(R.string.button_cancel)
    ) {
        onClick()
    }
}