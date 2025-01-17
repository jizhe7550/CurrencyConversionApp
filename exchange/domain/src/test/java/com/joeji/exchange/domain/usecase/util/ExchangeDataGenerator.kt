package com.joeji.exchange.domain.usecase.util

import com.joeji.exchange.domain.model.Currency

fun mockCurrencies() = listOf(
    Currency("USD", 1.0),
    Currency("EUR", 0.85),
    Currency("GBP", 0.75)
)