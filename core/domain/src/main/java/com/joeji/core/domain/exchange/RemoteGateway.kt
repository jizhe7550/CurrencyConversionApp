package com.joeji.core.domain.exchange

import com.joeji.core.domain.exchange.model.CurrencyResponse
import com.joeji.core.domain.util.DataError
import com.joeji.core.domain.util.Result

interface RemoteGateway {

    suspend fun fetchCurrencyList(): Result<CurrencyResponse, DataError.Network>

}