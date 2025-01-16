package com.joeji.exchange.data.gateway

import com.joeji.exchange.domain.model.Currency
import com.joeji.core.domain.util.DataError
import com.joeji.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

typealias CurrencyType = String

interface ExchangeLocalGateway {

    suspend fun saveCurrencyList(currencyList: List<Currency>): Result<List<CurrencyType>, DataError.Local>

    fun monitorCurrencyList(): Flow<List<Currency>>
}