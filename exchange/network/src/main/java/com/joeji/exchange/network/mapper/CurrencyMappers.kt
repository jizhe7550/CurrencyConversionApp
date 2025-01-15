package com.joeji.exchange.network.mapper

import com.joeji.core.domain.exchange.model.Currency
import com.joeji.exchange.network.dto.CurrencyDto

fun CurrencyDto.toCurrency(): Currency {
    return Currency(
        currencyType = currencyType,
        rate = rate,
    )
}