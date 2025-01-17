package com.joeji.exchange.domain.repository

import com.joeji.core.domain.util.DataError
import com.joeji.core.domain.util.EmptyResult
import com.joeji.exchange.domain.model.Currency
import kotlinx.coroutines.flow.Flow

interface ExchangeRepository {

    suspend fun fetchCurrencies(): EmptyResult<DataError>

    suspend fun monitorCurrencies(): Flow<List<Currency>>

    suspend fun saveCurrentRequestTime(timestamp: Long = System.currentTimeMillis())

    fun getLastRequestTime(): Long?

    suspend fun saveBaseCurrencyType(baseCurrency: String, forceUpdate: Boolean = false)

    suspend fun getBaseCurrencyType(): String
}