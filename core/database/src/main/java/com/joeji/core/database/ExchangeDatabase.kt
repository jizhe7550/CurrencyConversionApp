package com.joeji.core.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.joeji.core.database.dao.CurrencyDao
import com.joeji.core.database.entity.CurrencyEntity

const val DATABASE_NAME = "exchange_database"

@Database(
    entities = [
        CurrencyEntity::class,
    ],
    version = 1
)
abstract class ExchangeDatabase : RoomDatabase() {

    abstract fun currencyDao(): CurrencyDao

    companion object {

        fun init(
            context: Context,
        ): ExchangeDatabase = Room.databaseBuilder(
            context,
            ExchangeDatabase::class.java,
            DATABASE_NAME
        ).build()
    }
}