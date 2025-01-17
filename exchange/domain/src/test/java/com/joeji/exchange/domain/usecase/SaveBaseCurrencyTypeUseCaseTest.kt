package com.joeji.exchange.domain.usecase

import com.joeji.exchange.domain.repository.ExchangeRepository
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class SaveBaseCurrencyTypeUseCaseTest {

    private lateinit var underTest: SaveBaseCurrencyTypeUseCase
    private lateinit var mockExchangeRepository: ExchangeRepository

    @BeforeEach
    fun setUp() {
        mockExchangeRepository = mockk()
        underTest = SaveBaseCurrencyTypeUseCase(
            repository = mockExchangeRepository
        )
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `test that usecase calls saveBaseCurrencyType with correct parameters`() = runTest {
        val baseCurrency = "USD"
        val forceUpdate = true

        coEvery { mockExchangeRepository.saveBaseCurrencyType(baseCurrency, forceUpdate) } just Runs

        underTest.invoke(baseCurrency, forceUpdate)

        coVerify(exactly = 1) {
            mockExchangeRepository.saveBaseCurrencyType(
                baseCurrency,
                forceUpdate
            )
        }
    }

    @Test
    fun `test that usecase calls saveBaseCurrencyType with default forceUpdate value`() = runTest {
        val baseCurrency = "EUR"

        coEvery { mockExchangeRepository.saveBaseCurrencyType(baseCurrency, true) } just Runs

        underTest.invoke(baseCurrency)

        coVerify(exactly = 1) { mockExchangeRepository.saveBaseCurrencyType(baseCurrency, true) }
    }
}