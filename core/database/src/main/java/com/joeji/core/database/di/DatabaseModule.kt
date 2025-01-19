package com.joeji.core.database.di

import android.content.Context
import com.joeji.core.database.ExchangeDatabase
import com.joeji.core.database.dao.CurrencyDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideExchangeDatabase(@ApplicationContext context: Context): ExchangeDatabase {
        return ExchangeDatabase.init(
            context = context,
        )
    }

    @Provides
    @Singleton
    fun providerExchangeDao(database: ExchangeDatabase): CurrencyDao {
        return database.currencyDao()
    }
}