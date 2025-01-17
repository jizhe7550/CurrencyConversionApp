package com.joeji.exchange.domain.usecase

import com.joeji.exchange.domain.repository.ExchangeRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetBaseCurrencyTypeUseCaseTest {

    private lateinit var underTest: GetBaseCurrencyTypeUseCase
    private lateinit var mockExchangeRepository: ExchangeRepository

    @BeforeEach
    fun setUp() {
        mockExchangeRepository = mockk()
        underTest = GetBaseCurrencyTypeUseCase(
            repository = mockExchangeRepository
        )
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `test that usecase returns correct base currency type`() = runTest {
        val expectedBaseCurrency = "USD"
        coEvery { mockExchangeRepository.getBaseCurrencyType() } returns expectedBaseCurrency

        val result = underTest()

        assertEquals(expectedBaseCurrency, result)
    }
}