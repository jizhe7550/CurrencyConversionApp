package com.joeji.exchange.data.gateway

import com.joeji.core.data.BuildConfig
import com.joeji.core.data.network.get
import com.joeji.exchange.domain.model.Currency
import com.joeji.exchange.domain.model.CurrencyResponse
import com.joeji.core.domain.util.DataError
import com.joeji.core.domain.util.Result
import com.joeji.core.domain.util.map
import com.joeji.exchange.data.dto.CurrencyResponseDto
import io.ktor.client.HttpClient

class KtorRemoteGatewayImpl constructor(
    private val httpClient: HttpClient
) : ExchangeRemoteGateway {

    override suspend fun fetchCurrencyList(): Result<CurrencyResponse, DataError.Network> {
        return httpClient.get<CurrencyResponseDto>(
            route = "/latest.json?app_id=${BuildConfig.API_KEY}",
        ).map { dto ->
            CurrencyResponse(
                baseCurrency = dto.base,
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