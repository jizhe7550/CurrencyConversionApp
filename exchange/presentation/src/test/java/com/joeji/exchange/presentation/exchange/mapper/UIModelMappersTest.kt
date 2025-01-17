package com.joeji.exchange.presentation.exchange.mapper

import com.google.common.truth.Truth.assertThat
import com.joeji.exchange.domain.model.Currency
import com.joeji.exchange.presentation.exchange.model.CurrencyUIModel
import org.junit.jupiter.api.Test

class UIModelMappersTest {

    @Test
    fun `test that mapper will always keep 2 float number as string when rate more than 2 float number`() {
        val usd = Currency(
            currencyType = "USD",
            rate = 1.00000
        )
        val expected = CurrencyUIModel(
            currencyType = "USD",
            rate = "1.00"
        )

        assertThat(usd.toUIModel()).isEqualTo(expected)
    }

    @Test
    fun `test that mapper will always keep 2 float number as string when rate less than 2 float number`() {
        val usd = Currency(
            currencyType = "USD",
            rate = 1.0
        )
        val expected = CurrencyUIModel(
            currencyType = "USD",
            rate = "1.00"
        )

        assertThat(usd.toUIModel()).isEqualTo(expected)
    }

}