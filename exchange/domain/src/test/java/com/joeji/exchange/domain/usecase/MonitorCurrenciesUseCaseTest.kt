package com.joeji.exchange.domain.usecase

import app.cash.turbine.test
import com.joeji.core.testing.util.mockCurrencies
import com.joeji.exchange.domain.model.Currency
import com.joeji.exchange.domain.repository.ExchangeRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MonitorCurrenciesUseCaseTest {

    private lateinit var underTest: MonitorCurrenciesUseCase
    private lateinit var mockExchangeRepository: ExchangeRepository

    @BeforeEach
    fun setUp() {
        mockExchangeRepository = mockk()
        underTest = MonitorCurrenciesUseCase(
            repository = mockExchangeRepository
        )
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `test that usecase returns flow of currencies from repository`() = runTest {
        val currencies = mockCurrencies()
        coEvery { mockExchangeRepository.monitorCurrencies() } returns flowOf(currencies)

        underTest().test {
            val result = awaitItem()

            assertEquals(currencies, result)
            coVerify(exactly = 1) { mockExchangeRepository.monitorCurrencies() }

            awaitComplete()
        }
    }

    @Test
    fun `test that usecase handles empty currency list`() = runTest {
        val emptyCurrencies = emptyList<Currency>()
        coEvery { mockExchangeRepository.monitorCurrencies() } returns flowOf(emptyCurrencies)

        underTest().test {
            val result = awaitItem()

            assertEquals(emptyCurrencies, result)
            coVerify(exactly = 1) { mockExchangeRepository.monitorCurrencies() }

            awaitComplete()
        }
    }
}