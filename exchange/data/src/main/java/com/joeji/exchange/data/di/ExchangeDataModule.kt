package com.joeji.exchange.data.di

import com.joeji.core.common.di.qualifier.IoDispatcherQualifier
import com.joeji.exchange.data.gateway.ExchangeLocalGateway
import com.joeji.exchange.data.gateway.ExchangeRemoteGateway
import com.joeji.exchange.data.gateway.KtorRemoteGatewayImpl
import com.joeji.exchange.data.gateway.RoomLocalGatewayImpl
import com.joeji.exchange.data.repository.DefaultExchangeRepository
import com.joeji.exchange.domain.repository.ExchangeRepository
import org.koin.core.module.dsl.singleOf
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

    singleOf(::RoomLocalGatewayImpl).bind<ExchangeLocalGateway>()
    singleOf(::KtorRemoteGatewayImpl).bind<ExchangeRemoteGateway>()
}