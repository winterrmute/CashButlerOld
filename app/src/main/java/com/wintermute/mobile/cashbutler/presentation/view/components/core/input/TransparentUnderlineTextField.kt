package com.wintermute.mobile.cashbutler.presentation.view.components.core.input

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import com.wintermute.mobile.cashbutler.ui.theme.Typography

/**
 * Represents a input text component with transparent background and an underline
 *
 * @param modifier modification to root of this element
 * @param value initial value to on text field
 * @param placeholder to show when value is empty
 * @param fontSize can be changed when necessary
 * @param enabled clickable state of item
 * @param onValueChange action to take when the value changes
 */
@Composable
fun TransparentUnderlineTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    placeholder: String = "Input Text",
    fontSize: TextUnit = Typography.bodyLarge.fontSize,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit
) {

    var text by remember { mutableStateOf(value) }

    if (!enabled && text.isNotBlank()) {
        text = ""
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .then(modifier)
    ) {
        Column {
            Box {
                if (text.isEmpty()) {
                    Text(
                        text = placeholder,
                        color = Color.Gray,
                        style = TextStyle(
                            fontSize = fontSize
                        )
                    )
                }

                BasicTextField(
                    value = text,
                    enabled = enabled,
                    onValueChange = {
                        text = it
                        onValueChange(it)
                    },
                    textStyle = TextStyle(
                        fontSize = fontSize
                    ),
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.5f)
                    .padding(top = 5.dp)
                    .background(Color.Gray)
                    .height(2.dp)
            )
        }
    }
}