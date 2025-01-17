package com.joeji.exchange.presentation.exchange.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.joeji.core.presentation.ui.UiText
import com.joeji.core.presentation.ui.formatToTwoDecimalPlaces
import com.joeji.exchange.domain.model.Currency
import com.joeji.exchange.domain.usecase.FetchCurrenciesUseCase
import com.joeji.exchange.domain.usecase.GetBaseCurrencyTypeUseCase
import com.joeji.exchange.domain.usecase.MonitorCurrenciesUseCase
import com.joeji.exchange.domain.usecase.SaveBaseCurrencyTypeUseCase
import com.joeji.exchange.presentation.R
import com.joeji.exchange.presentation.exchange.mapper.toUIModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ExchangeViewModel(
    private val monitorCurrenciesUseCase: MonitorCurrenciesUseCase,
    private val fetchCurrenciesUseCase: FetchCurrenciesUseCase,
    private val getBaseCurrencyTypeUseCase: GetBaseCurrencyTypeUseCase,
    private val saveBaseCurrencyTypeUseCase: SaveBaseCurrencyTypeUseCase,
    private val defaultDispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _uiState = MutableStateFlow(ExchangeState())
    val uiState = _uiState.asStateFlow()

    private val eventChannel = Channel<ExchangeEvent>()
    val events = eventChannel.receiveAsFlow()

    private var amountChangeJob: Job? = null

    init {
        monitorCurrencies()
        fetchCurrencies()
    }

    private fun monitorCurrencies() {
        viewModelScope.launch {
            combine(
                monitorCurrenciesUseCase(),
                flowOf(getBaseCurrencyTypeUseCase())
            ) { currencies, baseCurrencyType ->
                val baseCurrency = currencies.find { it.currencyType == baseCurrencyType }
                currencies to baseCurrency
            }.catch { println("db issue - upload log") }
                .collectLatest { (currencies, baseCurrency) ->
                    _uiState.update {
                        it.copy(
                            baseCurrency = baseCurrency ?: it.baseCurrency,
                            currencies = currencies,
                            currencyUIModel = generateUiModel(
                                baseCurrency = baseCurrency ?: it.baseCurrency,
                                currencies = currencies,
                                validAmount = it.amount.toDouble()
                            ),
                        )
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
                        amount = formatToTwoDecimalPlaces(validAmount),
                    )
                }

                delay(100)

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
            } else {
                eventChannel.send(
                    ExchangeEvent.Error(
                        UiText.StringResource(
                            R.string.invalid_input_is_not_a_valid_number,
                            arrayOf(input)
                        )
                    )
                )
            }
        }
    }

    private suspend fun generateUiModel(
        currencies: List<Currency>,
        baseCurrency: Currency = _uiState.value.baseCurrency,
        validAmount: Double = _uiState.value.amount.toDouble(),
    ): List<CurrencyUIModel> = withContext(defaultDispatcher) {
        currencies.map { currency ->
            var newRate = currency.rate * validAmount / baseCurrency.rate
            newRate = if (newRate < 0.01) 0.01 else newRate
            currency.copy(rate = newRate).toUIModel()
        }
    }
}