package com.joeji.core.testing.util

import com.joeji.exchange.domain.model.Currency

fun mockCurrencies() = listOf(
    Currency("USD", 1.00),
    Currency("EUR", 0.85),
    Currency("GBP", 0.75)
)