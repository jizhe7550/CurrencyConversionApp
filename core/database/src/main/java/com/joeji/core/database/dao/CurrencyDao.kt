package com.joeji.core.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.joeji.core.database.entity.CurrencyEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyDao {

    @Upsert
    suspend fun upsertCurrency(currencyEntity: CurrencyEntity)

    @Upsert
    suspend fun upsertCurrencies(currencyEntities: List<CurrencyEntity>)

    @Query("SELECT * FROM currencyentity")
    fun getCurrencies(): Flow<List<CurrencyEntity>>

    @Query("DELETE FROM currencyentity WHERE currencyType=:currencyType")
    suspend fun delete(currencyType: String)

    @Query("DELETE FROM currencyentity")
    suspend fun deleteAllCurrencies()
}