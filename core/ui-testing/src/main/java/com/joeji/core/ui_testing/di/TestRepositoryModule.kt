package com.joeji.core.ui_testing.di

import com.joeji.exchange.data.di.RepositoryModule
import com.joeji.exchange.data.repository.DefaultExchangeRepository
import com.joeji.exchange.domain.repository.ExchangeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [RepositoryModule::class],
)
abstract class TestRepositoryModule {

    @Binds
    abstract fun bindExchangeRepository(implementation: DefaultExchangeRepository): ExchangeRepository

}