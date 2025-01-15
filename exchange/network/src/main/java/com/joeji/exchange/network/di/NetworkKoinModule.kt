package com.joeji.exchange.network.di

import com.joeji.core.domain.exchange.RemoteGateway
import com.joeji.exchange.network.KtorRemoteGatewayImpl
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val exchangeNetworkModule = module {
    singleOf(::KtorRemoteGatewayImpl).bind<RemoteGateway>()
}