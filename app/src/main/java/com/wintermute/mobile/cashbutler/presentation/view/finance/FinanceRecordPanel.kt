package com.wintermute.mobile.cashbutler.presentation.view.finance

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import arrow.core.Option
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialCategory
import com.wintermute.mobile.cashbutler.data.persistence.finance.FinancialRecord
import com.wintermute.mobile.cashbutler.presentation.view.components.dialog.FullScreenBottomSheetDialog
import com.wintermute.mobile.cashbutler.presentation.view.components.finance.FinanceRecordEntrySheet
import com.wintermute.mobile.cashbutler.presentation.view.components.finance.FinancialRecordCard
import com.wintermute.mobile.cashbutler.presentation.view.components.ui.AddButton

@Composable
fun FinanceRecordPanel(
    category: FinancialCategory,
    data: List<FinancialRecord>,
    onDismiss: () -> Unit,
    onAdd: (record: FinancialRecord) -> Unit,
    onUpdate: (record: FinancialRecord) -> Unit,
    onDelete: (record: FinancialRecord) -> Unit
) {
    var bottomSheetVisible by remember { mutableStateOf(false) }
    var selectedRecord by remember {
        mutableStateOf<Option<FinancialRecord>>(
            Option.fromNullable(
                null
            )
        )
    }

    FullScreenBottomSheetDialog(content = {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 16.dp),
                horizontalArrangement = Arrangement.End
            ) {
                IconButton(onClick = {
                    onDismiss()
                }) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Add",
                        tint = Color.Black,
                    )
                }
            }

            Text(
                text = category.name,
                style = TextStyle(
                    fontSize = 40.sp,
                    fontWeight = FontWeight.Bold
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .fillMaxWidth()
            )

            Text(
                text = "Balance: ${category.balance}",
                style = TextStyle(
                    fontStyle = FontStyle.Italic,
                    fontSize = 20.sp,
                ),
                modifier = Modifier.padding(horizontal = 8.dp)
            )
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(2.dp)
                    .padding(horizontal = 8.dp)
            ) {
                drawLine(
                    color = Color.Black,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = 2f
                )
            }

            LazyColumn {
                itemsIndexed(data) { index, item ->
                    FinancialRecordCard(
                        title = item.title,
                        amount = item.amount,
                        onClick = {
                            selectedRecord = Option.fromNullable(item)
                            bottomSheetVisible = true
                        },
                        onDeleteClick = { onDelete(item) }
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .wrapContentWidth(align = Alignment.End),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.Bottom
        ) {
            AddButton {
                bottomSheetVisible = true
            }
        }
    },
        bottomSheet = {
            if (bottomSheetVisible) {
                FinanceRecordEntrySheet(
                    record = selectedRecord,
                    onConfirm = { record ->
                        if (record.id != 0L) {
                            onUpdate(record)
                        } else {
                            onAdd(
                                record.copy(category = category.id)
                            )
                        }
                        bottomSheetVisible = false
                    },
                    onDismiss = { bottomSheetVisible = false }
                )
            }
        },
        showBottomSheet = bottomSheetVisible,
        onDismiss = { onDismiss() }
    )
}

