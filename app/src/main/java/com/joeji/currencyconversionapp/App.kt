package com.joeji.currencyconversionapp

import android.app.Application
import com.joeji.core.data.di.coreDataModule
import com.joeji.core.data.di.coroutineScopeModule
import com.joeji.core.data.di.preferencesModule
import com.joeji.core.database.di.databaseModule
import com.joeji.exchange.data.di.exchangeDataModule
import com.joeji.exchange.presentation.di.exchangeViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidLogger()
            androidContext(this@App)
            modules(
                coreDataModule,
                coroutineScopeModule,
                exchangeViewModelModule,
                exchangeDataModule,
                preferencesModule,
                databaseModule,
            )
        }
    }
}