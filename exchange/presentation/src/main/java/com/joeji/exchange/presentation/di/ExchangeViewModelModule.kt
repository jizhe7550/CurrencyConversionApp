package com.joeji.exchange.presentation.di

import com.joeji.core.common.di.qualifier.DefaultDispatcherQualifier
import com.joeji.exchange.domain.usecase.FetchCurrenciesUseCase
import com.joeji.exchange.domain.usecase.IsRequestAllowedUseCase
import com.joeji.exchange.domain.usecase.MonitorCurrenciesUseCase
import com.joeji.exchange.domain.usecase.SaveBaseCurrencyTypeUseCase
import com.joeji.exchange.domain.usecase.MonitorBaseCurrencyTypeUseCase
import com.joeji.exchange.presentation.model.ExchangeViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val exchangeViewModelModule = module {
    singleOf(::MonitorCurrenciesUseCase)
    singleOf(::FetchCurrenciesUseCase)
    singleOf(::IsRequestAllowedUseCase)
    singleOf(::MonitorBaseCurrencyTypeUseCase)
    singleOf(::SaveBaseCurrencyTypeUseCase)
    viewModel {
        ExchangeViewModel(
            monitorCurrenciesUseCase = get(),
            fetchCurrenciesUseCase = get(),
            monitorBaseCurrencyTypeUseCase = get(),
            saveBaseCurrencyTypeUseCase = get(),
            defaultDispatcher = get(DefaultDispatcherQualifier),
        )
    }
}