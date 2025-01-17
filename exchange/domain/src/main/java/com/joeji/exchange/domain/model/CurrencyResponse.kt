package com.joeji.exchange.domain.model

data class CurrencyResponse(
    val baseCurrency: String,
    val currencies: List<Currency>,
)
