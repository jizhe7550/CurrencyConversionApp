package com.joeji.exchange.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyResponseDto(
    val base: String,
    val rates: Map<String, Double>,
)
