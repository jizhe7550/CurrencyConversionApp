package com.joeji.exchange.network.dto


import kotlinx.serialization.Serializable

@Serializable
data class CurrencyDto(
    val currencyType: String,
    val rate: Double,
)
