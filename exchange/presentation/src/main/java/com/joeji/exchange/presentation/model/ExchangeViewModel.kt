package com.joeji.exchange.presentation.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeji.core.presentation.ui.UiText
import com.joeji.core.presentation.ui.formatToTwoDecimalPlaces
import com.joeji.exchange.domain.model.Currency
import com.joeji.exchange.domain.usecase.FetchCurrenciesUseCase
import com.joeji.exchange.domain.usecase.MonitorBaseCurrencyTypeUseCase
import com.joeji.exchange.domain.usecase.MonitorCurrenciesUseCase
import com.joeji.exchange.domain.usecase.SaveBaseCurrencyTypeUseCase
import com.joeji.exchange.presentation.R
import com.joeji.exchange.presentation.mapper.toUIModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExchangeViewModel(
    private val monitorCurrenciesUseCase: MonitorCurrenciesUseCase,
    private val fetchCurrenciesUseCase: FetchCurrenciesUseCase,
    private val monitorBaseCurrencyTypeUseCase: MonitorBaseCurrencyTypeUseCase,
    private val saveBaseCurrencyTypeUseCase: SaveBaseCurrencyTypeUseCase,
    private val defaultDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExchangeState())
    val uiState = _uiState.asStateFlow()

    private val eventChannel = Channel<ExchangeEvent>()
    val events = eventChannel.receiveAsFlow()

    private var amountChangeJob: Job? = null

    init {
        fetchCurrencies()
        monitorCurrencies()
    }

    private fun monitorCurrencies() {
        viewModelScope.launch {
            combine(
                monitorCurrenciesUseCase(),
                monitorBaseCurrencyTypeUseCase(),
            ) { currencies, baseCurrencyType ->
                val baseCurrency =
                    currencies.find { it.currencyType == baseCurrencyType }
                currencies to baseCurrency
            }.catch {
                println("db issue - upload log")
            }.onCompletion {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                    )
                }
            }.collectLatest { (currencies, baseCurrency) ->
                if (currencies.isNotEmpty()) {
                    _uiState.update {
                        it.copy(
                            baseCurrency = baseCurrency ?: it.baseCurrency,
                            currencies = currencies,
                            currencyUIModel = generateUiModel(
                                baseCurrency = baseCurrency ?: it.baseCurrency,
                                currencies = currencies,
                                validAmount = it.amount.toDouble(),
                            ),
                            isLoading = false,
                        )
                    }
                }
            }
        }
    }

    private fun fetchCurrencies() {
        viewModelScope.launch {
            fetchCurrenciesUseCase()
        }
    }

    fun onCurrencySelected(selectedCurrency: Currency) {
        viewModelScope.launch {
            val currencies = _uiState.value.currencies
            val currentAmount = _uiState.value.amount
            val uiModel = generateUiModel(
                baseCurrency = selectedCurrency,
                currencies = currencies,
                validAmount = currentAmount.toDouble()
            )
            _uiState.update {
                it.copy(
                    baseCurrency = selectedCurrency,
                    currencyUIModel = uiModel,
                )
            }
            saveBaseCurrencyTypeUseCase(selectedCurrency.currencyType)
        }
    }

    fun onAmountChange(input: String) {
        amountChangeJob?.cancel()
        amountChangeJob = viewModelScope.launch {
            val validAmount = input.toDoubleOrNull()
            if (validAmount != null) {
                _uiState.update {
                    it.copy(
                        amount = validAmount.formatToTwoDecimalPlaces(),
                    )
                }
                delay(100) //debounce
                handleValidAmount(validAmount)
            } else {
                warningInvalidInput(input)
            }
        }
    }

    private suspend fun warningInvalidInput(input: String) {
        eventChannel.send(
            ExchangeEvent.Error(
                UiText.StringResource(
                    R.string.invalid_input_is_not_a_valid_number,
                    arrayOf(input)
                )
            )
        )
    }

    private suspend fun handleValidAmount(validAmount: Double) {
        val currencies = _uiState.value.currencies
        val baseCurrency = _uiState.value.baseCurrency
        val uiModel = generateUiModel(
            baseCurrency = baseCurrency,
            currencies = currencies,
            validAmount = validAmount
        )
        _uiState.update {
            it.copy(
                currencyUIModel = uiModel
            )
        }
    }

    private suspend fun generateUiModel(
        currencies: List<Currency>,
        baseCurrency: Currency = _uiState.value.baseCurrency,
        validAmount: Double = _uiState.value.amount.toDouble(),
    ): List<CurrencyUIModel> = withContext(defaultDispatcher) {
        currencies.map { currency ->
            currency.toUIModel(validAmount, baseCurrency)
        }
    }
}