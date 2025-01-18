package com.joeji.core.data.di

import com.joeji.core.data.datastore.AppPreferencesDatastore
import com.joeji.core.data.gateway.PreferencesGateway
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GatewayModule {

    @Binds
    abstract fun bindPreferencesGateway(implementation: AppPreferencesDatastore): PreferencesGateway
}