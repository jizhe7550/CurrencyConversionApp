package com.joeji.exchange.data.di

import com.joeji.exchange.data.repository.DefaultExchangeRepository
import com.joeji.exchange.domain.repository.ExchangeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindExchangeRepository(implementation: DefaultExchangeRepository): ExchangeRepository

}