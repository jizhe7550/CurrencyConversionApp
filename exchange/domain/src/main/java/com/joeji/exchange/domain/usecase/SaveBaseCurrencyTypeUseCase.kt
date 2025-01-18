package com.joeji.exchange.domain.usecase

import com.joeji.exchange.domain.repository.ExchangeRepository
import javax.inject.Inject

class SaveBaseCurrencyTypeUseCase @Inject constructor(
    private val repository: ExchangeRepository,
) {
    suspend operator fun invoke(baseCurrency: String, forceUpdate: Boolean = true) =
        repository.saveBaseCurrencyType(
            baseCurrency = baseCurrency,
            forceUpdate = forceUpdate,
        )
}