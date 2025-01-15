package com.joeji.core.database

import android.database.sqlite.SQLiteFullException
import com.joeji.core.database.dao.CurrencyDao
import com.joeji.core.database.mapper.toCurrency
import com.joeji.core.database.mapper.toCurrencyEntity
import com.joeji.core.domain.exchange.model.Currency
import com.joeji.core.domain.exchange.CurrencyType
import com.joeji.core.domain.exchange.LocalGateway
import com.joeji.core.domain.util.DataError
import com.joeji.core.domain.util.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class RoomLocalGatewayImpl constructor(
    private val currencyDao: CurrencyDao
) : LocalGateway {

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