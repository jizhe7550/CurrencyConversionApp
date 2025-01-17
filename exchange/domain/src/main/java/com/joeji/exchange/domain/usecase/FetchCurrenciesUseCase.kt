package com.joeji.exchange.domain.usecase

import com.joeji.exchange.domain.repository.ExchangeRepository

class FetchCurrenciesUseCase constructor(
    private val isRequestAllowedUseCase: IsRequestAllowedUseCase,
    private val repository: ExchangeRepository,
) {
    suspend operator fun invoke() {
        while (isRequestAllowedUseCase()){
            repository.fetchCurrencies()
        }
    }
}