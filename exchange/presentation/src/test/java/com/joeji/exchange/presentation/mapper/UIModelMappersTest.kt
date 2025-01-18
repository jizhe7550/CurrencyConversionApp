package com.joeji.exchange.presentation.mapper

import com.google.common.truth.Truth.assertThat
import com.joeji.exchange.domain.model.Currency
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.ValueSource

class UIModelMappersTest {

    @Test
    fun `test that toUIModel with valid amount and base currency`() {
        val baseCurrency = Currency(currencyType = "USD", rate = 1.0)
        val currency = Currency(currencyType = "EUR", rate = 0.9)
        val validAmount = 100.0

        val result = currency.toUIModel(validAmount, baseCurrency)

        assertThat(result.currencyType).isEqualTo("EUR")
        assertThat(result.rate).isEqualTo("90.00")
    }


    @ParameterizedTest
    @ValueSource(doubles = [0.0, -1.0, -100.0])
    fun `test that toUIModel with rate less than min value`(input: Double) {
        val baseCurrency = Currency(currencyType = "USD", rate = 1.0)
        val currency = Currency(currencyType = "EUR", rate = 0.0001)

        val result = currency.toUIModel(input, baseCurrency)

        assertThat(result.currencyType).isEqualTo("EUR")
        assertThat(result.rate).isEqualTo("0.00")
    }

}