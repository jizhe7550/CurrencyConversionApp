package com.joeji.core.database.mapper

import com.joeji.core.database.entity.CurrencyEntity
import com.joeji.core.domain.exchange.model.Currency

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