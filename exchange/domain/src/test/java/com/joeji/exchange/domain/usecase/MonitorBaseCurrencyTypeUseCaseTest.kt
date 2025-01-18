package com.joeji.exchange.domain.usecase

import com.joeji.exchange.domain.repository.ExchangeRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MonitorBaseCurrencyTypeUseCaseTest {

    private lateinit var underTest: MonitorBaseCurrencyTypeUseCase
    private lateinit var mockExchangeRepository: ExchangeRepository

    @BeforeEach
    fun setUp() {
        mockExchangeRepository = mockk()
        underTest = MonitorBaseCurrencyTypeUseCase(
            repository = mockExchangeRepository
        )
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `test that usecase returns correct base currency type`() = runTest {
        val expectedBaseCurrency = "USD"
        coEvery { mockExchangeRepository.monitorBaseCurrencyType() } returns flowOf(
            expectedBaseCurrency
        )

        val result = underTest().first()

        assertEquals(expectedBaseCurrency, result)
    }
}