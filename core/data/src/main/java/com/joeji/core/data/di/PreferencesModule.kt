package com.joeji.core.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.joeji.core.common.di.qualifier.IoDispatcherQualifier
import com.joeji.core.data.datastore.AppPreferencesDatastore
import com.joeji.core.data.gateway.PreferencesGateway
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.bind
import org.koin.dsl.module

private const val APP_PREFERENCE_DATASTORE_NAME = "app_preferences"

val Context.myDataStore: DataStore<Preferences> by preferencesDataStore(name = APP_PREFERENCE_DATASTORE_NAME)

val preferencesModule = module {
    single<DataStore<Preferences>> {
        val context: Context = androidApplication()
        context.myDataStore
    }

    single {
        AppPreferencesDatastore(
            appPreferencesDatastore = get(),
            ioDispatcher = get(IoDispatcherQualifier),
        )
    }.bind<PreferencesGateway>()
}