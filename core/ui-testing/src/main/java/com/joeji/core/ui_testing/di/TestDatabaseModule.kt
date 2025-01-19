package com.joeji.core.ui_testing.di

import android.content.Context
import androidx.room.Room
import com.joeji.core.database.ExchangeDatabase
import com.joeji.core.database.dao.CurrencyDao
import com.joeji.core.database.di.DatabaseModule
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [DatabaseModule::class],
)
object TestDatabaseModule {

    @Provides
    @Singleton
    fun provideUserDatabase(@ApplicationContext context: Context): ExchangeDatabase {
        return Room.inMemoryDatabaseBuilder(
            context, ExchangeDatabase::class.java
        ).build()
    }

    @Provides
    @Singleton
    fun providerUserDao(database: ExchangeDatabase): CurrencyDao {
        return database.currencyDao()
    }
}