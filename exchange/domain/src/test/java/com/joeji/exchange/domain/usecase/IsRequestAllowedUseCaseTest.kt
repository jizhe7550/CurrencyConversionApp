package com.joeji.exchange.domain.usecase

import com.joeji.exchange.domain.repository.ExchangeRepository
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

/**
 * This test case is showing how to use fake
 */
class IsRequestAllowedUseCaseTest {

    private lateinit var underTest: IsRequestAllowedUseCase
    private lateinit var mockExchangeRepository: ExchangeRepository

    @BeforeEach
    fun setUp() {
        mockExchangeRepository = mockk()
        underTest = IsRequestAllowedUseCase(
            repository = mockExchangeRepository
        )
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `test that usecase should return true when last request time is null,which is login at the first time`() =
        runTest {
            coEvery { mockExchangeRepository.getLastRequestTime() } returns null

            val result = underTest()

            assertTrue(
                result,
                "Should return true when there is no last request time because login at first time"
            )
        }

    @Test
    fun `test that usecase should return true when more than 30 minutes have passed since last request`() =
        runTest {
            val currentTime = System.currentTimeMillis()
            val lastRequestTime =
                currentTime - IsRequestAllowedUseCase.THIRTY_MINUTES_IN_MILLIS - 1000

            coEvery { mockExchangeRepository.getLastRequestTime() } returns lastRequestTime

            val result = underTest()

            assertTrue(
                result,
                "Should return true when more than 30 minutes have passed since last request"
            )
        }

    @Test
    fun `test that usecase should return false when less than 30 minutes have passed since last request`() =
        runTest {
            val currentTime = System.currentTimeMillis()
            val lastRequestTime =
                currentTime - IsRequestAllowedUseCase.THIRTY_MINUTES_IN_MILLIS + 1000

            coEvery { mockExchangeRepository.getLastRequestTime() } returns lastRequestTime

            val result = underTest()

            assertFalse(
                result,
                "Should return false when less than 30 minutes have passed since last request"
            )
        }
}