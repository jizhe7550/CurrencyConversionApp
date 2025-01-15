package com.joeji.exchange.data.di

import com.joeji.core.data.di.qualifier.IoDispatcherQualifier
import com.joeji.exchange.data.repository.DefaultExchangeRepository
import com.joeji.exchange.domain.repository.ExchangeRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val exchangeDataModule = module {
    single {
        DefaultExchangeRepository(
            ioDispatcher = get(IoDispatcherQualifier),
            localGateway = get(),
            remoteGateway = get(),
            preferencesGateway = get(),
        )
    }.bind<ExchangeRepository>()
}