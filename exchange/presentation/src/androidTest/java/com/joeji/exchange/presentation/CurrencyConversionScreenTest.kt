package com.joeji.exchange.presentation

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import com.joeji.core.presentation.designsystem.CurrencyConversionAppTheme
import com.joeji.core.testing.util.mockCurrencies
import com.joeji.exchange.domain.model.Currency
import com.joeji.exchange.presentation.mapper.toUIModel
import com.joeji.exchange.presentation.model.ExchangeState
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CurrencyConversionScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    private val mockBaseCurrency = Currency(currencyType = "AED", rate = 3.67)
    private val mockCurrenciesList = mockCurrencies().map {
        it.toUIModel(validAmount = 100.00, baseCurrency = mockBaseCurrency)
    }

    private val mockState = ExchangeState(
        isLoading = false,
        baseCurrency = mockBaseCurrency,
        currencyUIModel = mockCurrenciesList,
        amount = "100.00",
        currencies = mockCurrencies()
    )

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun testEnterAmountField_isDisplayed() {
        composeRule.setContent {
            CurrencyConversionAppTheme {
                CurrencyConversionScreen(
                    uiState = mockState
                )
            }
        }

        composeRule.onNodeWithText("Enter Amount").assertIsDisplayed()
    }

    @Test
    fun testLoadingState_showsProgressIndicator() {
        val loadingState = mockState.copy(isLoading = true)

        composeRule.setContent {
            CurrencyConversionAppTheme {
                CurrencyConversionScreen(
                    uiState = loadingState
                )
            }
        }

        composeRule.onNodeWithContentDescription("Progress Indicator").assertIsDisplayed()
    }

    @Test
    fun testCurrencyDropDownMenu_isDisplayed() {
        composeRule.setContent {
            CurrencyConversionAppTheme {
                CurrencyConversionScreen(
                    uiState = mockState
                )
            }
        }

        composeRule.onNodeWithText(mockState.baseCurrency.currencyType).assertIsDisplayed()
    }
}