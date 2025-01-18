package com.joeji.exchange.data.gateway

import android.database.sqlite.SQLiteFullException
import com.joeji.core.database.dao.CurrencyDao
import com.joeji.core.domain.util.DataError
import com.joeji.core.domain.util.Result
import com.joeji.exchange.data.mapper.toCurrency
import com.joeji.exchange.data.mapper.toCurrencyEntity
import com.joeji.exchange.domain.model.Currency
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RoomLocalGatewayImpl @Inject constructor(
    private val currencyDao: CurrencyDao
) : ExchangeLocalGateway {

    override suspend fun saveCurrencyList(currencyList: List<Currency>): Result<List<CurrencyType>, DataError.Local> {
        return try {
            val entities = currencyList.map { it.toCurrencyEntity() }
            currencyDao.upsertCurrencies(entities)
            Result.Success(entities.map { it.currencyType })
        } catch (e: SQLiteFullException) {
            Result.Error(DataError.Local.DISK_FULL)
        }
    }

    override fun monitorCurrencyList(): Flow<List<Currency>> {
        return currencyDao.getCurrencies()
            .map { currencyEntities ->
                currencyEntities.map { it.toCurrency() }
            }
    }
}