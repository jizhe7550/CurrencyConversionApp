package com.joeji.exchange.domain.model

data class CurrencyResponse(
    val baseCurrency: String,
    val timestamp: Long,
    val currencies: List<Currency>,
)
