package com.joeji.exchange.data.repository

import com.joeji.core.data.gateway.PreferencesGateway
import com.joeji.core.domain.util.DataError
import com.joeji.core.domain.util.EmptyResult
import com.joeji.core.domain.util.Result.Error
import com.joeji.core.domain.util.Result.Success
import com.joeji.core.domain.util.asEmptyDataResult
import com.joeji.exchange.data.gateway.ExchangeLocalGateway
import com.joeji.exchange.data.gateway.ExchangeRemoteGateway
import com.joeji.exchange.domain.model.Currency
import com.joeji.exchange.domain.repository.ExchangeRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DefaultExchangeRepository constructor(
    private val remoteGateway: ExchangeRemoteGateway,
    private val localGateway: ExchangeLocalGateway,
    private val preferencesGateway: PreferencesGateway,
    private val ioDispatcher: CoroutineDispatcher,
) : ExchangeRepository {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(ioDispatcher + job)

    private var cachedLastRequestTime: Long? = null

    init {
        monitorLastRequestTime()
    }

    private fun monitorLastRequestTime() {
        scope.launch {
            preferencesGateway.monitorLong(LAST_REQUEST_TIME).collect { newTime ->
                cachedLastRequestTime = newTime
            }
        }
    }

    override suspend fun fetchCurrencies(fetchTimestamp: Long?): EmptyResult<DataError> =
        withContext(ioDispatcher) {
            when (val result = remoteGateway.fetchCurrencyList()) {
                is Error -> result.asEmptyDataResult()
                is Success -> {
                    val deferred = async {
                        localGateway.saveCurrencyList(result.data.currencies).asEmptyDataResult()
                    }.await()
                    launch {
                        saveCurrentRequestTime(fetchTimestamp ?: System.currentTimeMillis())
                    }
                    launch {
                        saveBaseCurrencyType(result.data.baseCurrency)
                    }

                    deferred.asEmptyDataResult()
                }
            }
        }

    override suspend fun monitorCurrencies(): Flow<List<Currency>> = withContext(ioDispatcher) {
        localGateway.monitorCurrencyList()
    }

    private suspend fun saveCurrentRequestTime(timestamp: Long) = withContext(ioDispatcher) {
        preferencesGateway.putLong(LAST_REQUEST_TIME, timestamp)
    }

    override suspend fun getLastRequestTime(): Long? = withContext(ioDispatcher) {
        if (cachedLastRequestTime == null) {
            preferencesGateway.monitorLong(LAST_REQUEST_TIME).first()
        } else {
            cachedLastRequestTime
        }
    }

    override suspend fun saveBaseCurrencyType(baseCurrency: String, forceUpdate: Boolean) =
        withContext(ioDispatcher) {
            if (forceUpdate || getBaseCurrencyType().isEmpty()) {
                preferencesGateway.putString(BASE_CURRENCY_TYPE, baseCurrency)
            }
        }

    private suspend fun getBaseCurrencyType(): String = withContext(ioDispatcher) {
        preferencesGateway.monitorString(BASE_CURRENCY_TYPE, "").first().toString()
    }

    override suspend fun monitorBaseCurrencyType(): Flow<String?> = withContext(ioDispatcher) {
        preferencesGateway.monitorString(BASE_CURRENCY_TYPE, "")
    }

    companion object {
        const val LAST_REQUEST_TIME = "lastRequestTime"
        const val BASE_CURRENCY_TYPE = "baseCurrencyType"
    }
}