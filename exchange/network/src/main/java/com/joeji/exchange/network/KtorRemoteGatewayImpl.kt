package com.joeji.exchange.network

import com.joeji.core.data.BuildConfig
import com.joeji.core.data.network.get
import com.joeji.core.domain.exchange.RemoteGateway
import com.joeji.core.domain.exchange.model.Currency
import com.joeji.core.domain.exchange.model.CurrencyResponse
import com.joeji.core.domain.util.DataError
import com.joeji.core.domain.util.Result
import com.joeji.core.domain.util.map
import com.joeji.exchange.network.dto.CurrencyResponseDto
import io.ktor.client.HttpClient

class KtorRemoteGatewayImpl constructor(
    private val httpClient: HttpClient
) : RemoteGateway {

    override suspend fun fetchCurrencyList(): Result<CurrencyResponse, DataError.Network> {
        return httpClient.get<CurrencyResponseDto>(
            route = "/latest.json?app_id=${BuildConfig.API_KEY}",
        ).map { dto ->
            CurrencyResponse(
                baseCurrency = dto.base,
                timestamp = dto.timestamp,
                currencies = dto.rates.map { (currencyType, rate) ->
                    Currency(
                        currencyType = currencyType,
                        rate = rate,
                    )
                }
            )
        }
    }

}