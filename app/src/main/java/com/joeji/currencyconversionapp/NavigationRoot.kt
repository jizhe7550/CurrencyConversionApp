package com.joeji.currencyconversionapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.joeji.exchange.presentation.CurrencyConversionScreenRoot

@Composable
fun NavigationRoot(
    navController: NavHostController,
) {
    NavHost(
        navController = navController,
        startDestination = Routes.Exchange
    ) {
        exchangeScreen()
    }
}

private fun NavGraphBuilder.exchangeScreen() {
    composable<Routes.Exchange> {
        CurrencyConversionScreenRoot()
    }
}
