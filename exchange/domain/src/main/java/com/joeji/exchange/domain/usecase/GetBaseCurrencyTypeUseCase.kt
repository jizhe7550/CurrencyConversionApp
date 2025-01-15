package com.joeji.exchange.domain.usecase

import com.joeji.exchange.domain.repository.ExchangeRepository

class GetBaseCurrencyTypeUseCase constructor(
    private val repository: ExchangeRepository,
) {
    suspend operator fun invoke(): String =
        repository.getBaseCurrencyType()
}