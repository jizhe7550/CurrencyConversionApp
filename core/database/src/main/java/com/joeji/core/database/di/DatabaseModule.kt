package com.joeji.core.database.di

import androidx.room.Room
import com.joeji.core.database.ExchangeDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val databaseModule = module {
    single {
        Room.databaseBuilder(
            androidApplication(),
            ExchangeDatabase::class.java,
            "exchange.db"
        ).build()
    }
    single { get<ExchangeDatabase>().currencyDao }
}