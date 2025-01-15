package com.joeji.core.domain.exchange.model

data class CurrencyResponse(
    val baseCurrency: String,
    val timestamp: Long,
    val currencies: List<Currency>,
)
