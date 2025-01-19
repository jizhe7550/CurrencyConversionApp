package com.joeji.exchange.data.di

import com.joeji.exchange.data.gateway.ExchangeLocalGateway
import com.joeji.exchange.data.gateway.ExchangeRemoteGateway
import com.joeji.exchange.data.gateway.KtorRemoteGatewayImpl
import com.joeji.exchange.data.gateway.RoomLocalGatewayImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class GatewayModule {

    @Binds
    abstract fun bindExchangeLocalGateway(implementation: RoomLocalGatewayImpl): ExchangeLocalGateway

    @Binds
    abstract fun bindExchangeRemoteGateway(implementation: KtorRemoteGatewayImpl): ExchangeRemoteGateway

}