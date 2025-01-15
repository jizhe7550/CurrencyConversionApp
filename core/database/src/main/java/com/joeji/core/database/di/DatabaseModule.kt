package com.joeji.core.database.di

import androidx.room.Room
import com.joeji.core.database.ExchangeDatabase
import com.joeji.core.database.RoomLocalGatewayImpl
import com.joeji.core.domain.exchange.LocalGateway
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
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

    singleOf(::RoomLocalGatewayImpl).bind<LocalGateway>()
}