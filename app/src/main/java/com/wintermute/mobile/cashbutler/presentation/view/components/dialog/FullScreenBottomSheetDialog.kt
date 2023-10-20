package com.wintermute.mobile.cashbutler.presentation.view.components.dialog

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomSheetScaffold
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FullScreenBottomSheetDialog(
    content: @Composable () -> Unit,
    bottomSheet: @Composable () -> Unit,
    showBottomSheet: Boolean,
    onDismiss: () -> Unit
) {
    val bottomSheetScaffoldState = rememberBottomSheetScaffoldState()
    val coroutineScope = rememberCoroutineScope()

    DisposableEffect(showBottomSheet) {
        if (showBottomSheet) {
            coroutineScope.launch {
                bottomSheetScaffoldState.bottomSheetState.expand()
            }
        } else {
            coroutineScope.launch {
                bottomSheetScaffoldState.bottomSheetState.collapse()
            }
        }
        onDispose {}
    }

    Dialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = { onDismiss() }
    ) {
        BottomSheetScaffold(
            scaffoldState = bottomSheetScaffoldState,
            sheetContent = {
                bottomSheet()
            },
            sheetShape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp),
            sheetPeekHeight = 0.dp,
            sheetGesturesEnabled = true,
            modifier = Modifier.fillMaxSize()
        ) {
            Surface(modifier = Modifier.fillMaxSize()) {
                content()
            }
        }
    }
}