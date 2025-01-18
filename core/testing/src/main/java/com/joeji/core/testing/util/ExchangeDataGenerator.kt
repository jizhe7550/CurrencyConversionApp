package com.joeji.core.testing.util

import com.joeji.exchange.domain.model.Currency

fun mockCurrencies() = listOf(
    Currency("USD", 1.00),
    Currency("EUR", 0.85),
    Currency("GBP", 0.75),
)

fun usdCurrency() = Currency("USD", 1.00)
fun eurCurrency() = Currency("EUR", 0.85)
fun gbpCurrency() = Currency("GBP", 0.75)
