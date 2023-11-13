package com.wintermute.mobile.cashbutler.presentation.view.components.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.LargeFloatingActionButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.wintermute.mobile.cashbutler.ui.theme.appGreen
import com.wintermute.mobile.cashbutler.ui.theme.appRed

/**
 * Bottom sheet dialog to handle creating or editing operations and separate the editable sheets
 * from stale view
 *
 * @param onConfirm action to take when user confirms the process
 * @param onDismiss action to take when user aborts the process
 * @param content actual editable content as composable
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheetDialogScaffold(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
    content: @Composable () -> Unit
) {
    val bottomSheetState =
        rememberModalBottomSheetState(
            skipPartiallyExpanded = true
//            confirmValueChange = { false }
        )

    ModalBottomSheet(
        modifier = Modifier.fillMaxHeight(0.8f),
        onDismissRequest = onDismiss,
        sheetState = bottomSheetState
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            LargeFloatingActionButton(
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.End)
                    .size(32.dp),
                containerColor = appRed,
                onClick = { onDismiss() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = "confirm button",
                    tint = Color.White
                )
            }

            Box(modifier = Modifier.padding(8.dp)) {
                content()
            }

            Spacer(modifier = Modifier.weight(1f))

            LargeFloatingActionButton(
                modifier = Modifier
                    .padding(32.dp)
                    .align(Alignment.End)
                    .size(48.dp),
                containerColor = appGreen,
                onClick = { onConfirm() }
            ) {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = "confirm button",
                    tint = Color.White
                )
            }
        }
    }
}