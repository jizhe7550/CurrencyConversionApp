package com.joeji.exchange.presentation.model

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.joeji.core.testing.util.MainCoroutineExtension
import com.joeji.core.testing.util.mockCurrencies
import com.joeji.exchange.domain.model.Currency
import com.joeji.exchange.domain.usecase.FetchCurrenciesUseCase
import com.joeji.exchange.domain.usecase.GetBaseCurrencyTypeUseCase
import com.joeji.exchange.domain.usecase.MonitorCurrenciesUseCase
import com.joeji.exchange.domain.usecase.SaveBaseCurrencyTypeUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainCoroutineExtension::class)
class ExchangeViewModelTest {

    private lateinit var underTest: ExchangeViewModel
    private lateinit var monitorCurrenciesUseCase: MonitorCurrenciesUseCase
    private lateinit var fetchCurrenciesUseCase: FetchCurrenciesUseCase
    private lateinit var getBaseCurrencyTypeUseCase: GetBaseCurrencyTypeUseCase
    private lateinit var saveBaseCurrencyTypeUseCase: SaveBaseCurrencyTypeUseCase

    @BeforeEach
    fun setUp() {
        monitorCurrenciesUseCase = mockk()
        fetchCurrenciesUseCase = mockk()
        getBaseCurrencyTypeUseCase = mockk()
        saveBaseCurrencyTypeUseCase = mockk()
        commonStubs()
        initViewModel()
    }

    private fun initViewModel() {
        underTest = ExchangeViewModel(
            monitorCurrenciesUseCase = monitorCurrenciesUseCase,
            fetchCurrenciesUseCase = fetchCurrenciesUseCase,
            getBaseCurrencyTypeUseCase = getBaseCurrencyTypeUseCase,
            saveBaseCurrencyTypeUseCase = saveBaseCurrencyTypeUseCase,
            defaultDispatcher = StandardTestDispatcher()
        )
    }

    private fun commonStubs() {
        coEvery { monitorCurrenciesUseCase() } returns flowOf(emptyList())
        coEvery { getBaseCurrencyTypeUseCase() } returns "USD"
        coEvery { fetchCurrenciesUseCase() } just Runs
        coEvery { saveBaseCurrencyTypeUseCase(any(), any()) } just Runs
    }

    @Test
    fun `test that initial state is set`() = runTest {
        underTest.uiState.test {
            val initialState = awaitItem()
            assertThat(initialState.baseCurrency).isEqualTo(Currency("USD", 1.0))
            assertThat(initialState.amount).isEqualTo("1.00")
            assertThat(initialState.currencyUIModel).isEmpty()
            assertThat(initialState.currencies).isEmpty()
        }
    }

    @Test
    fun `test that monitorCurrencies works fine`() = runTest {
        val baseCurrency = Currency("EUR", 0.85)
        val mockCurrencies = mockCurrencies()
        val amount = "1.00"
        val mockUiModel = underTest.generateUiModel(
            currencies = mockCurrencies,
            baseCurrency = baseCurrency,
            validAmount = amount.toDouble()
        )
        coEvery { monitorCurrenciesUseCase() } returns flowOf(mockCurrencies)
        coEvery { getBaseCurrencyTypeUseCase() } returns baseCurrency.currencyType
        initViewModel()

        advanceUntilIdle()

        underTest.uiState.test {
            val updateState = awaitItem()
            assertThat(updateState.baseCurrency).isEqualTo(baseCurrency)
            assertThat(updateState.amount).isEqualTo(amount)
            assertThat(updateState.currencyUIModel).isEqualTo(mockUiModel)
            assertThat(updateState.currencies).isEqualTo(mockCurrencies())
        }
    }

    @Test
    fun `test that onCurrencySelected updates uiState`() = runTest {
        val defaultState = ExchangeState()
        val selectedCurrency = Currency("EUR", 0.85)
        val mockCurrencies = mockCurrencies()
        val uiModel = underTest.generateUiModel(
            currencies = mockCurrencies,
            baseCurrency = selectedCurrency,
            validAmount = defaultState.amount.toDouble()
        )
        coEvery { monitorCurrenciesUseCase() } returns flowOf(mockCurrencies)
        coEvery { getBaseCurrencyTypeUseCase() } returns defaultState.baseCurrency.currencyType
        initViewModel()
        advanceUntilIdle()

        underTest.onCurrencySelected(selectedCurrency)

        underTest.uiState.drop(1).test {
            val state = awaitItem()
            assertThat(state.baseCurrency).isEqualTo(selectedCurrency)
            assertThat(state.currencyUIModel).isEqualTo(uiModel)
        }
    }

    @Test
    fun `test that onAmountChange updates uiState and triggers generateUiModel when input is valid`() =
        runTest {
            val defaultState = ExchangeState()
            val mockCurrencies = mockCurrencies()
            val input = "100"
            val uiModel = underTest.generateUiModel(
                currencies = mockCurrencies,
                baseCurrency = defaultState.baseCurrency,
                validAmount = input.toDouble()
            )

            coEvery { monitorCurrenciesUseCase() } returns flowOf(mockCurrencies)
            coEvery { getBaseCurrencyTypeUseCase() } returns defaultState.baseCurrency.currencyType

            initViewModel()
            advanceUntilIdle()

            underTest.onAmountChange(input)

            advanceUntilIdle()
            val expectInput = "100.00"

            underTest.uiState.test {
                val state = awaitItem()
                assertThat(state.amount).isEqualTo(expectInput)
                assertThat(state.currencyUIModel).isEqualTo(uiModel)
            }
        }

    @Test
    fun `test that onAmountChange triggers error event when input is invalid`() = runTest {
        val invalidInput = "invalid"

        underTest.onAmountChange(invalidInput)

        underTest.events.test {
            val event = awaitItem()
            assertThat(event).isInstanceOf(ExchangeEvent.Error::class.java)
        }
    }

    @Test
    fun `test that fetchCurrencies calls use case`() = runTest {
        initViewModel()
        coVerify { fetchCurrenciesUseCase() }
    }
}