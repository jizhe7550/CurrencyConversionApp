package com.joeji.exchange.domain.usecase

import com.joeji.exchange.domain.usecase.util.FakeExchangeRepository
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class IsRequestAllowedUseCaseTest {

    private lateinit var underTest: IsRequestAllowedUseCase
    private val fakeExchangeRepository = FakeExchangeRepository()

    @BeforeEach
    fun setUp() {
        underTest = IsRequestAllowedUseCase(
            repository = fakeExchangeRepository
        )
    }

    @AfterEach
    fun tearDown() {
        fakeExchangeRepository.resetFake()
    }

    @Test
    fun `test that usecase should return true when last request time is null,which is login at the first time`() =
        runBlocking {
            fakeExchangeRepository.setCurrentRequestTime(null)

            val result = underTest()

            assertTrue(
                result,
                "Should return true when there is no last request time because login at first time"
            )
        }

    @Test
    fun `test that usecase should return true when more than 30 minutes have passed since last request`() =
        runBlocking {
            val currentTime = System.currentTimeMillis()
            val lastRequestTime =
                currentTime - IsRequestAllowedUseCase.THIRTY_MINUTES_IN_MILLIS - 1000

            // Mock last request
            fakeExchangeRepository.saveCurrentRequestTime(lastRequestTime)

            val result = underTest()

            assertTrue(
                result,
                "Should return true when more than 30 minutes have passed since last request"
            )
        }

    @Test
    fun `test that usecase should return false when less than 30 minutes have passed since last request`() =
        runBlocking {
            val currentTime = System.currentTimeMillis()
            val lastRequestTime =
                currentTime - IsRequestAllowedUseCase.THIRTY_MINUTES_IN_MILLIS + 1000  // 小于30分钟

            // Mock last request
            fakeExchangeRepository.saveCurrentRequestTime(lastRequestTime)


            val result = underTest()

            assertFalse(
                result,
                "Should return false when less than 30 minutes have passed since last request"
            )
        }
}