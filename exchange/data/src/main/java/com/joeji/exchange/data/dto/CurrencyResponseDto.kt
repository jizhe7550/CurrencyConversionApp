package com.joeji.exchange.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class CurrencyResponseDto(
    val base: String,
    val timestamp: Long,
    val rates: Map<String, Double>,
)
