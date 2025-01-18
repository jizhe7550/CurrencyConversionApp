package com.joeji.exchange.presentation.model

data class CurrencyUIModel(
    val currencyType: String,
    val rate: String,
) {
    val key
        get() = currencyType
}
