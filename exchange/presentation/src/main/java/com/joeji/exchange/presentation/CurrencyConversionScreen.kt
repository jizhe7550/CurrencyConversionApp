package com.joeji.exchange.presentation

import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.joeji.core.presentation.designsystem.CurrencyConversionAppTheme
import com.joeji.core.presentation.designsystem.component.DefaultListItem
import com.joeji.core.presentation.designsystem.component.DefaultTextField
import com.joeji.core.presentation.ui.ObserveAsEvents
import com.joeji.exchange.domain.model.Currency
import com.joeji.exchange.presentation.model.CurrencyUIModel
import com.joeji.exchange.presentation.model.ExchangeEvent
import com.joeji.exchange.presentation.model.ExchangeState
import com.joeji.exchange.presentation.model.ExchangeViewModel

@Composable
fun CurrencyConversionScreenRoot(
    viewModel: ExchangeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    ObserveAsEvents(viewModel.events) { event ->
        when (event) {
            is ExchangeEvent.Error -> {
                Toast.makeText(
                    context,
                    event.error.asString(context),
                    Toast.LENGTH_SHORT
                ).show()
            }

            ExchangeEvent.SyncSuccess -> {
                // TODO
            }
        }
    }

    CurrencyConversionScreen(
        uiState = uiState,
        onClick = viewModel::onCurrencySelected,
        onValueChange = viewModel::onAmountChange,
    )
}

@VisibleForTesting
@Composable
fun CurrencyConversionScreen(
    uiState: ExchangeState,
    onClick: (Currency) -> Unit = {},
    onValueChange: (String) -> Unit = {},
) {

    if (uiState.isLoading) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            CircularProgressIndicator(
                modifier = Modifier.semantics {
                    contentDescription = "Progress Indicator"
                }
            )
        }
    } else {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            DefaultTextField(
                text = uiState.amount,
                title = stringResource(R.string.currency_coversion_screen_text_field_title_enter_amount),
                modifier = Modifier.fillMaxWidth(),
                onValueChange = onValueChange,
                keyboardType = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
            )

            Spacer(modifier = Modifier.height(8.dp))

            CurrencyDropDownMenu(
                currencies = uiState.currencies,
                baseCurrency = uiState.baseCurrency,
                onClick = onClick,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(
                modifier = Modifier
                    .height(16.dp)
            )

            CurrencyLazyList(
                currencies = uiState.currencyUIModel,
            )
        }
    }
}

@Composable
private fun CurrencyDropDownMenu(
    currencies: List<Currency>,
    baseCurrency: Currency,
    onClick: (Currency) -> Unit,
    modifier: Modifier = Modifier,
) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
    ) {
        OutlinedButton(
            onClick = { expanded = !expanded },
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.TopEnd)
        ) {
            Text(baseCurrency.currencyType)
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            currencies.forEach { currency ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = currency.currencyType,
                        )
                    },
                    onClick = {
                        expanded = false
                        onClick(currency)
                    },
                    trailingIcon = {
                        if (baseCurrency.currencyType == currency.currencyType) {
                            Icon(Icons.Default.Check, contentDescription = null)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun CurrencyLazyList(
    currencies: List<CurrencyUIModel>,
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth(),
        state = rememberLazyListState(),
    ) {
        items(
            count = currencies.size,
            key = { currencies[it].key }
        ) { index ->
            val item = currencies[index]

            HorizontalDivider()
            DefaultListItem(
                leftText = item.currencyType,
                rightText = item.rate,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
            )
        }
    }
}

@Preview
@Composable
private fun CurrencyConversionActionScreenPreview() {
    CurrencyConversionAppTheme {
        CurrencyConversionScreen(
            uiState = ExchangeState(),
        )
    }
}