package com.joeji.exchange.presentation.exchange.model

data class CurrencyUIModel(
    val currencyType: String,
    val rate: String,
) {
    val key
        get() = currencyType
}
