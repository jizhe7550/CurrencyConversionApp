package com.joeji.exchange.presentation.model

import com.joeji.core.presentation.ui.UiText

sealed interface ExchangeEvent {
    data class Error(val error: UiText): ExchangeEvent
    data object SyncSuccess: ExchangeEvent
}