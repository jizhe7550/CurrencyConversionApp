package com.joeji.exchange.presentation.model

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.joeji.core.testing.MainCoroutineExtension
import com.joeji.core.testing.util.mockCurrencies
import com.joeji.exchange.domain.model.Currency
import com.joeji.exchange.domain.usecase.FetchCurrenciesUseCase
import com.joeji.exchange.domain.usecase.MonitorBaseCurrencyTypeUseCase
import com.joeji.exchange.domain.usecase.MonitorCurrenciesUseCase
import com.joeji.exchange.domain.usecase.SaveBaseCurrencyTypeUseCase
import com.joeji.exchange.presentation.mapper.toUIModel
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
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.ValueSource

@OptIn(ExperimentalCoroutinesApi::class)
@ExtendWith(MainCoroutineExtension::class)
class ExchangeViewModelTest {

    private lateinit var underTest: ExchangeViewModel
    private lateinit var monitorCurrenciesUseCase: MonitorCurrenciesUseCase
    private lateinit var fetchCurrenciesUseCase: FetchCurrenciesUseCase
    private lateinit var monitorBaseCurrencyTypeUseCase: MonitorBaseCurrencyTypeUseCase
    private lateinit var saveBaseCurrencyTypeUseCase: SaveBaseCurrencyTypeUseCase

    @BeforeEach
    fun setUp() {
        monitorCurrenciesUseCase = mockk()
        fetchCurrenciesUseCase = mockk()
        monitorBaseCurrencyTypeUseCase = mockk()
        saveBaseCurrencyTypeUseCase = mockk()
        commonStubs()
        initViewModel()
    }

    private fun initViewModel() {
        underTest = ExchangeViewModel(
            monitorCurrenciesUseCase = monitorCurrenciesUseCase,
            fetchCurrenciesUseCase = fetchCurrenciesUseCase,
            monitorBaseCurrencyTypeUseCase = monitorBaseCurrencyTypeUseCase,
            saveBaseCurrencyTypeUseCase = saveBaseCurrencyTypeUseCase,
            defaultDispatcher = StandardTestDispatcher()
        )
    }

    private fun commonStubs() {
        coEvery { monitorCurrenciesUseCase() } returns flowOf(emptyList())
        coEvery { monitorBaseCurrencyTypeUseCase() } returns flowOf("USD")
        coEvery { fetchCurrenciesUseCase() } just Runs
        coEvery { saveBaseCurrencyTypeUseCase(any(), any()) } just Runs
    }

    @Test
    fun `test that initial state is set`() = runTest {
        underTest.uiState.test {
            val initialState = awaitItem()
            assertThat(initialState.baseCurrency).isEqualTo(Currency("USD", 1.0))
            assertThat(initialState.amount).isEqualTo("100.00")
            assertThat(initialState.currencyUIModel).isEmpty()
            assertThat(initialState.currencies).isEmpty()
            assertThat(initialState.isLoading).isTrue()
        }
    }

    @Test
    fun `test that monitorCurrencies works fine`() = runTest {
        val baseCurrency = Currency("EUR", 0.85)
        val mockCurrencies = mockCurrencies()
        val amount = "100.00"
        val uiModel = mockCurrencies.map {
            it.toUIModel(
                validAmount = amount.toDouble(),
                baseCurrency = baseCurrency
            )
        }
        coEvery { monitorCurrenciesUseCase() } returns flowOf(mockCurrencies)
        coEvery { monitorBaseCurrencyTypeUseCase() } returns flowOf(baseCurrency.currencyType)
        initViewModel()

        advanceUntilIdle()

        underTest.uiState.test {
            val updateState = awaitItem()
            assertThat(updateState.baseCurrency).isEqualTo(baseCurrency)
            assertThat(updateState.amount).isEqualTo(amount)
            assertThat(updateState.currencyUIModel).isEqualTo(uiModel)
            assertThat(updateState.currencies).isEqualTo(mockCurrencies())
            assertThat(updateState.isLoading).isFalse()
        }
    }

    @Test
    fun `test that onCurrencySelected updates uiState`() = runTest {
        val defaultState = ExchangeState()
        val selectedCurrency = Currency("EUR", 0.85)
        val mockCurrencies = mockCurrencies()
        val uiModel = mockCurrencies.map {
            it.toUIModel(
                validAmount = defaultState.amount.toDouble(),
                baseCurrency = selectedCurrency
            )
        }
        coEvery { monitorCurrenciesUseCase() } returns flowOf(mockCurrencies)
        coEvery { monitorBaseCurrencyTypeUseCase() } returns flowOf(defaultState.baseCurrency.currencyType)
        initViewModel()
        advanceUntilIdle()

        underTest.onCurrencySelected(selectedCurrency)

        underTest.uiState.drop(1).test {
            val state = awaitItem()
            assertThat(state.baseCurrency).isEqualTo(selectedCurrency)
            assertThat(state.currencyUIModel).isEqualTo(uiModel)
        }
    }

    @ParameterizedTest
    @CsvSource(
        "100, 100.00",
        "-100, -100.00",
        "0, 0.00",
    )
    fun `test that onAmountChange updates uiState and triggers generateUiModel when input is valid`(
        input: String, expectedInput: String,
    ) =
        runTest {
            val defaultState = ExchangeState()
            val mockCurrencies = mockCurrencies()
            val uiModel = mockCurrencies.map {
                it.toUIModel(
                    validAmount = input.toDouble(),
                    baseCurrency = defaultState.baseCurrency
                )
            }

            coEvery { monitorCurrenciesUseCase() } returns flowOf(mockCurrencies)
            coEvery { monitorBaseCurrencyTypeUseCase() } returns flowOf(defaultState.baseCurrency.currencyType)

            initViewModel()
            advanceUntilIdle()

            underTest.onAmountChange(input)

            advanceUntilIdle()

            underTest.uiState.test {
                val state = awaitItem()
                assertThat(state.amount).isEqualTo(expectedInput)
                assertThat(state.currencyUIModel).isEqualTo(uiModel)
            }
        }

    @Test
    fun `test that onAmountChange updates uiState and triggers generateUiModel when input is zero`() =
        runTest {
            val defaultState = ExchangeState()
            val mockCurrencies = mockCurrencies()
            val input = "0.00"
            val uiModel = mockCurrencies.map {
                it.toUIModel(
                    validAmount = input.toDouble(),
                    baseCurrency = defaultState.baseCurrency
                )
            }

            coEvery { monitorCurrenciesUseCase() } returns flowOf(mockCurrencies)
            coEvery { monitorBaseCurrencyTypeUseCase() } returns flowOf(defaultState.baseCurrency.currencyType)

            initViewModel()
            advanceUntilIdle()

            underTest.onAmountChange(input)

            advanceUntilIdle()

            val expectedInput = "0.00"
            underTest.uiState.test {
                val state = awaitItem()
                assertThat(state.amount).isEqualTo(expectedInput)
                assertThat(state.currencyUIModel).isEqualTo(uiModel)
            }
        }

    @Test
    fun `test that onAmountChange updates uiState and triggers generateUiModel when input is a negative number`() =
        runTest {
            val defaultState = ExchangeState()
            val mockCurrencies = mockCurrencies()
            val input = "-0.10"
            val uiModel = mockCurrencies.map {
                it.toUIModel(
                    validAmount = input.toDouble(),
                    baseCurrency = defaultState.baseCurrency
                )
            }

            coEvery { monitorCurrenciesUseCase() } returns flowOf(mockCurrencies)
            coEvery { monitorBaseCurrencyTypeUseCase() } returns flowOf(defaultState.baseCurrency.currencyType)

            initViewModel()
            advanceUntilIdle()

            underTest.onAmountChange(input)

            advanceUntilIdle()

            val expectedInput = "-0.10"
            underTest.uiState.test {
                val state = awaitItem()
                assertThat(state.amount).isEqualTo(expectedInput)
                assertThat(state.currencyUIModel).isEqualTo(uiModel)
            }
        }

    @ParameterizedTest
    @ValueSource(strings = ["invalidInput", "##"])
    fun `test that onAmountChange triggers error event when input is invalid input`(invalidInput: String) =
        runTest {
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