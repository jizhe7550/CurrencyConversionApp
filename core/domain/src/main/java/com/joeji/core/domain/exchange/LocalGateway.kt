package com.joeji.core.domain.exchange

import com.joeji.core.domain.exchange.model.Currency
import com.joeji.core.domain.util.DataError
import com.joeji.core.domain.util.Result
import kotlinx.coroutines.flow.Flow

typealias CurrencyType = String

interface LocalGateway {

    suspend fun saveCurrencyList(currencyList: List<Currency>): Result<List<CurrencyType>, DataError.Local>

    fun monitorCurrencyList(): Flow<List<Currency>>
}