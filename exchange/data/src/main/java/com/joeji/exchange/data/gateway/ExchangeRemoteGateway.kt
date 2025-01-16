package com.joeji.exchange.data.gateway

import com.joeji.exchange.domain.model.CurrencyResponse
import com.joeji.core.domain.util.DataError
import com.joeji.core.domain.util.Result

interface ExchangeRemoteGateway {

    suspend fun fetchCurrencyList(): Result<CurrencyResponse, DataError.Network>

}