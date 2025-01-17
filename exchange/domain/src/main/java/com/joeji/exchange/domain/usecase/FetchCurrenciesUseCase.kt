package com.joeji.exchange.domain.usecase

import com.joeji.exchange.domain.repository.ExchangeRepository
import kotlinx.coroutines.delay

class FetchCurrenciesUseCase constructor(
    private val isRequestAllowedUseCase: IsRequestAllowedUseCase,
    private val repository: ExchangeRepository,
) {
    suspend operator fun invoke() {
        while (true) {
            if (isRequestAllowedUseCase()) {
                repository.fetchCurrencies()
            }
            delay(CHECKING_INTERVAL)
        }
    }

    companion object {
        const val CHECKING_INTERVAL = 2000L
    }
}