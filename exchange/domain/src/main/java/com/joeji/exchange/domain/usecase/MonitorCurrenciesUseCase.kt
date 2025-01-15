package com.joeji.exchange.domain.usecase

import com.joeji.core.domain.exchange.model.Currency
import com.joeji.exchange.domain.repository.ExchangeRepository
import kotlinx.coroutines.flow.Flow

class MonitorCurrenciesUseCase constructor(
    private val repository: ExchangeRepository,
) {
    suspend operator fun invoke(): Flow<List<Currency>> {
        return repository.monitorCurrencies()
    }
}