package com.joeji.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.joeji.core.database.dao.CurrencyDao
import com.joeji.core.database.entity.CurrencyEntity

@Database(
    entities = [
        CurrencyEntity::class,
    ],
    version = 1
)
abstract class ExchangeDatabase : RoomDatabase() {

    abstract val currencyDao: CurrencyDao
}