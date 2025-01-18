package com.joeji.exchange.presentation.model

import androidx.compose.runtime.Immutable
import com.joeji.exchange.domain.model.Currency

@Immutable
data class ExchangeState(
    val isLoading: Boolean = true,
    val amount: String = "100.00",
    val baseCurrency: Currency = Currency("USD", 1.00),
    val currencies: List<Currency> = emptyList(),
    val currencyUIModel: List<CurrencyUIModel> = emptyList(),
)