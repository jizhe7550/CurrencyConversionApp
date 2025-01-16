package com.joeji.exchange.presentation.exchange.mapper

import com.joeji.exchange.domain.model.Currency
import com.joeji.core.presentation.ui.formatToTwoDecimalPlaces
import com.joeji.exchange.presentation.exchange.model.CurrencyUIModel

fun Currency.toUIModel(): CurrencyUIModel {
    return CurrencyUIModel(
        currencyType = currencyType,
        rate = formatToTwoDecimalPlaces(rate),
    )
}