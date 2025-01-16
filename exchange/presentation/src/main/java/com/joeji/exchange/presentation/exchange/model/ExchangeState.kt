package com.joeji.exchange.presentation.exchange.model

import androidx.compose.runtime.Immutable
import com.joeji.exchange.domain.model.Currency

@Immutable
data class ExchangeState(
    val amount: String = "1.00",
    val baseCurrency: Currency = Currency("USD", 1.00),
    val currencies: List<Currency> = emptyList(),
    val currencyUIModel: List<CurrencyUIModel> = emptyList(),
)