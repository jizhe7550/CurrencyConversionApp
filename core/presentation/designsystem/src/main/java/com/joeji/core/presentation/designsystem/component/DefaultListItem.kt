package com.joeji.core.presentation.designsystem.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.joeji.core.presentation.designsystem.CurrencyConversionAppTheme

@Composable
fun DefaultListItem(
    leftText: String?,
    rightText: String?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        leftText?.let {
            Text(
                text = it,
            )
        }
        rightText?.let {
            Text(it)
        }
    }
}

@Preview
@Composable
private fun DefaultListItemPreview() {
    CurrencyConversionAppTheme {
        DefaultListItem(
            leftText = "USD",
            rightText = "12.55",
            modifier = Modifier
                .fillMaxWidth().padding(horizontal = 20.dp, vertical = 4.dp)
        )
    }
}
