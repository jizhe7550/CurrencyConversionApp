package com.joeji.exchange.domain.usecase

import com.joeji.exchange.domain.repository.ExchangeRepository

class IsRequestAllowedUseCase constructor(
    private val repository: ExchangeRepository,
) {
    operator fun invoke(): Boolean {
        val lastRequestTime = repository.getLastRequestTime()
        return if (lastRequestTime == null) {
            true
        } else {
            System.currentTimeMillis() - lastRequestTime > THIRTY_MINUTES_IN_MILLIS
        }
    }

    companion object {
        const val THIRTY_MINUTES_IN_MILLIS = 30 * 60 * 1000
    }
}