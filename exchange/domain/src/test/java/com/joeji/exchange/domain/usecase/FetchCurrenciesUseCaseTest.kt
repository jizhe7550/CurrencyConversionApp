package com.joeji.exchange.domain.usecase

import com.joeji.core.testing.MainCoroutineExtension
import com.joeji.exchange.domain.repository.ExchangeRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainCoroutineExtension::class)
class FetchCurrenciesUseCaseTest {

    private lateinit var underTest: FetchCurrenciesUseCase
    private lateinit var mockExchangeRepository: ExchangeRepository
    private lateinit var isRequestAllowedUseCase: IsRequestAllowedUseCase
    private val testDispatcher = StandardTestDispatcher()

    @BeforeEach
    fun setUp() {
        isRequestAllowedUseCase = mockk()
        mockExchangeRepository = mockk(relaxed = true)
        underTest = FetchCurrenciesUseCase(
            repository = mockExchangeRepository,
            isRequestAllowedUseCase = isRequestAllowedUseCase,
        )
    }

    @AfterEach
    fun tearDown() {
    }

    @Test
    fun `test that usecase calls fetchCurrencies when request is allowed`() = runTest {
        coEvery { isRequestAllowedUseCase() } returns true andThen false

        val job = launch(testDispatcher) {
            underTest()
        }

        advanceTimeBy(FetchCurrenciesUseCase.CHECKING_INTERVAL)

        coVerify(exactly = 1) { mockExchangeRepository.fetchCurrencies() }

        job.cancel()
    }

    @Test
    fun `testThatUseCase does not call fetchCurrencies when request is not allowed`() =
        runTest {
            coEvery { isRequestAllowedUseCase() } returns false

            val job = launch(testDispatcher) {
                underTest()
            }

            advanceTimeBy(FetchCurrenciesUseCase.CHECKING_INTERVAL)

            coVerify(exactly = 0) { mockExchangeRepository.fetchCurrencies() }

            job.cancel()
        }

    @Test
    fun `testThatUseCase calls fetchCurrencies multiple times with multiple allowed checks`() =
        runTest {
            coEvery { isRequestAllowedUseCase() } returns true andThen true andThen false

            val job = launch(testDispatcher) {
                underTest()
            }

            advanceTimeBy(FetchCurrenciesUseCase.CHECKING_INTERVAL * 3)

            coVerify(exactly = 2) { mockExchangeRepository.fetchCurrencies() }

            job.cancel()
        }

    @Test
    fun `testThatUseCase stops calling fetchCurrencies when request is no longer allowed`() =
        runTest {
            coEvery { isRequestAllowedUseCase() } returns true andThen false

            val job = launch(testDispatcher) {
                underTest()
            }

            advanceTimeBy(FetchCurrenciesUseCase.CHECKING_INTERVAL * 2)

            coVerify(exactly = 1) { mockExchangeRepository.fetchCurrencies() }

            job.cancel()
        }
}