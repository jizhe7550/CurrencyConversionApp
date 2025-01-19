package com.joeji.exchange.data.gateway

import com.google.common.truth.Truth.assertThat
import com.joeji.core.ui_testing.ExchangeAndroidTest
import com.joeji.core.database.dao.CurrencyDao
import com.joeji.core.database.di.DatabaseModule
import com.joeji.exchange.data.mapper.toCurrencyEntity
import com.joeji.exchange.domain.model.Currency
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import javax.inject.Inject

@UninstallModules(DatabaseModule::class)
@HiltAndroidTest
class RoomLocalGatewayImplTest : com.joeji.core.ui_testing.ExchangeAndroidTest() {

    @Inject
    lateinit var currencyDao: CurrencyDao

    @Inject
    lateinit var underTest: RoomLocalGatewayImpl


    @Test
    fun test_that_saveCurrencyList_should_save_currency_data_and_return_success() = runTest {
        val currencyList = listOf(Currency("USD", 1.0))
        val currencyEntities = currencyList.map { it.toCurrencyEntity() }

        underTest.saveCurrencyList(currencyList)

        val currencyEntitiesFromDb = currencyDao.getCurrencies().first()
        assertThat(currencyEntitiesFromDb)
            .isEqualTo(currencyEntities)
    }
}