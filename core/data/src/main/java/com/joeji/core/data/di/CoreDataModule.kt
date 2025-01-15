package com.joeji.core.data.di

import com.joeji.core.data.network.HttpClientFactory
import org.koin.dsl.module

val coreDataModule = module {
    single {
        HttpClientFactory().build()
    }
}