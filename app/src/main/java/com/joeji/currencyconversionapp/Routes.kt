package com.joeji.currencyconversionapp

import kotlinx.serialization.Serializable

sealed interface Routes {

    @Serializable
    data object Exchange : Routes
}