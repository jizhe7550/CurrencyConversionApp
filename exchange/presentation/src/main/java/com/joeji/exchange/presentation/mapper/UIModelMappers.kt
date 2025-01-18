package com.joeji.exchange.presentation.mapper

import com.joeji.core.presentation.ui.formatToTwoDecimalPlaces
import com.joeji.exchange.domain.model.Currency
import com.joeji.exchange.presentation.model.CurrencyUIModel

fun Currency.toUIModel(validAmount: Double, baseCurrency: Currency): CurrencyUIModel {
    val newRate = maxOf(rate * validAmount / baseCurrency.rate, 0.00)
    return CurrencyUIModel(
        currencyType = currencyType,
        rate = newRate.formatToTwoDecimalPlaces()
    )
}