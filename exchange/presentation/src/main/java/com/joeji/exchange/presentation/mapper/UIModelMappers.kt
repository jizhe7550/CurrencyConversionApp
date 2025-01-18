package com.joeji.exchange.presentation.mapper

import com.joeji.core.presentation.ui.formatToTwoDecimalPlaces
import com.joeji.exchange.domain.model.Currency
import com.joeji.exchange.presentation.model.CurrencyUIModel

fun Currency.toUIModel(): CurrencyUIModel {
    return CurrencyUIModel(
        currencyType = currencyType,
        rate = rate.formatToTwoDecimalPlaces(),
    )
}