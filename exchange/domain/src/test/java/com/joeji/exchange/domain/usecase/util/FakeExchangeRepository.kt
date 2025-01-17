package com.joeji.exchange.domain.usecase.util

import com.joeji.core.domain.util.DataError
import com.joeji.core.domain.util.EmptyResult
import com.joeji.core.domain.util.Result
import com.joeji.core.domain.util.asEmptyDataResult
import com.joeji.exchange.domain.model.Currency
import com.joeji.exchange.domain.repository.ExchangeRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeExchangeRepository : ExchangeRepository {

    private val mockCurrencies = listOf(
        Currency("USD", 1.0),
        Currency("EUR", 0.85),
        Currency("GBP", 0.75)
    )

    private var lastRequestTime: Long? = null
    private var baseCurrency: String = ""

    override suspend fun fetchCurrencies(): EmptyResult<DataError> {
        return Result.Success(mockCurrencies.map { it.currencyType }).asEmptyDataResult()
    }

    override suspend fun monitorCurrencies(): Flow<List<Currency>> {
        return flowOf(mockCurrencies)
    }

    override suspend fun saveCurrentRequestTime(timestamp: Long) {
        lastRequestTime = timestamp
    }

    override fun getLastRequestTime(): Long? {
        return lastRequestTime
    }

    override suspend fun saveBaseCurrencyType(baseCurrency: String, forceUpdate: Boolean) {
        if (forceUpdate || this.baseCurrency.isEmpty()) {
            this.baseCurrency = baseCurrency
        }
    }

    override suspend fun getBaseCurrencyType(): String {
        return baseCurrency
    }

    fun setCurrentRequestTime(timestamp: Long?) {
        lastRequestTime = timestamp
    }

    fun resetFake() {
        lastRequestTime = null
        baseCurrency = ""
    }
}
