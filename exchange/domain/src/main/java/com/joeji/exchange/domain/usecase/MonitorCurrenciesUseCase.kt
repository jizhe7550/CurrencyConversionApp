package com.joeji.exchange.domain.usecase

import com.joeji.exchange.domain.model.Currency
import com.joeji.exchange.domain.repository.ExchangeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MonitorCurrenciesUseCase @Inject constructor(
    private val repository: ExchangeRepository,
) {
    suspend operator fun invoke(): Flow<List<Currency>> {
        return repository.monitorCurrencies()
    }
}