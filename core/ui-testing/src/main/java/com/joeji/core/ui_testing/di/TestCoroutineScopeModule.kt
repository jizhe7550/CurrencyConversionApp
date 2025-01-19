package com.joeji.core.ui_testing.di

import com.joeji.core.common.di.CoroutineScopeModule
import com.joeji.core.common.di.qualifier.DefaultDispatcher
import com.joeji.core.common.di.qualifier.IoDispatcher
import com.joeji.core.common.di.qualifier.MainDispatcher
import com.joeji.core.common.di.qualifier.MainImmediateDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers


@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [CoroutineScopeModule::class],
)
object TestCoroutineScopeModule {

    @IoDispatcher
    @Provides
    fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

    @MainDispatcher
    @Provides
    fun provideMainDispatcher(): CoroutineDispatcher = Dispatchers.Main

    @MainImmediateDispatcher
    @Provides
    fun provideMainImmediateDispatcher(): CoroutineDispatcher = Dispatchers.Main.immediate

    @DefaultDispatcher
    @Provides
    fun provideDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

}