package com.joeji.exchange.data.mapper

import com.joeji.core.database.entity.CurrencyEntity
import com.joeji.exchange.domain.model.Currency
import com.joeji.exchange.data.dto.CurrencyDto

fun CurrencyDto.toCurrency(): Currency {
    return Currency(
        currencyType = currencyType,
        rate = rate,
    )
}

fun CurrencyEntity.toCurrency(): Currency {
    return Currency(
        currencyType = currencyType,
        rate = rate
    )
}

fun Currency.toCurrencyEntity(): CurrencyEntity {
    return CurrencyEntity(
        currencyType = currencyType,
        rate = rate
    )
}