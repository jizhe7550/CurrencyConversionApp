package com.joeji.core.data.di

import com.joeji.core.data.di.qualifier.DefaultDispatcherQualifier
import com.joeji.core.data.di.qualifier.IoDispatcherQualifier
import com.joeji.core.data.di.qualifier.MainDispatcherQualifier
import com.joeji.core.data.di.qualifier.MainImmediateDispatcherQualifier
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.dsl.module


val coroutineScopeModule = module {
    single<CoroutineDispatcher>(IoDispatcherQualifier) {
        Dispatchers.IO
    }
    single<CoroutineDispatcher>(DefaultDispatcherQualifier) {
        Dispatchers.Default
    }
    single<CoroutineDispatcher>(MainDispatcherQualifier) {
        Dispatchers.Main
    }
    single<CoroutineDispatcher>(MainImmediateDispatcherQualifier) {
        Dispatchers.Main.immediate
    }
}