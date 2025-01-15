package com.joeji.core.presentation.designsystem.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.joeji.core.presentation.designsystem.CurrencyConversionAppTheme

@Composable
fun DefaultTextField(
    text: String,
    title: String?,
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    keyboardType: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
) {
    var isFocused by remember {
        mutableStateOf(false)
    }
    Column(
        modifier = modifier
    ) {
        if (title != null) {
            Text(
                text = title,
            )
        }

        TextField(
            value = text,
            onValueChange = onValueChange,
            keyboardOptions = keyboardType,
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(
                    MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.05f)
                )
                .border(
                    width = 1.dp,
                    color = if (isFocused) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.secondary
                    },
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(12.dp)
                .onFocusChanged { focusState ->
                    isFocused = focusState.isFocused
                }
                .fillMaxWidth(),
            textStyle = TextStyle(
                color = Color.Black,
                fontSize = 16.sp,
                textAlign = TextAlign.End
            ),
        )
    }
}

@Preview
@Composable
private fun DefaultTextFieldPreview() {
    CurrencyConversionAppTheme {
        DefaultTextField(
            text = "1.00",
            title = "Enter Amount",
            modifier = Modifier
                .fillMaxWidth(),
        )
    }
}
