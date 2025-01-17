package com.joeji.exchange.domain.usecase

import com.joeji.exchange.domain.usecase.util.FakeExchangeRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class GetBaseCurrencyTypeUseCaseTest {

    private lateinit var underTest: GetBaseCurrencyTypeUseCase
    private val fakeExchangeRepository = FakeExchangeRepository()

    @BeforeEach
    fun setUp() {
        underTest = GetBaseCurrencyTypeUseCase(
            repository = fakeExchangeRepository
        )
    }

    @AfterEach
    fun tearDown() {
        fakeExchangeRepository.resetFake()
    }

    @Test
    fun `test that usecase returns correct base currency type`() = runTest {
        val expectedBaseCurrency = fakeExchangeRepository.getBaseCurrencyType()

        val result = underTest()

        assertEquals(expectedBaseCurrency, result)
    }
}