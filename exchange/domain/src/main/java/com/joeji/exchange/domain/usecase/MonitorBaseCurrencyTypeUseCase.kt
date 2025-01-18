package com.joeji.exchange.domain.usecase

import com.joeji.exchange.domain.repository.ExchangeRepository
import kotlinx.coroutines.flow.Flow

class MonitorBaseCurrencyTypeUseCase constructor(
    private val repository: ExchangeRepository,
) {
    suspend operator fun invoke(): Flow<String?> =
        repository.monitorBaseCurrencyType()
}