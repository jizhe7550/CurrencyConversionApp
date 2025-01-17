package com.joeji.exchange.data.repository

import com.joeji.core.data.gateway.PreferencesGateway
import com.joeji.core.domain.util.Result
import com.joeji.core.testing.util.MainCoroutineExtension
import com.joeji.core.testing.util.mockCurrencies
import com.joeji.exchange.data.gateway.ExchangeLocalGateway
import com.joeji.exchange.data.gateway.ExchangeRemoteGateway
import com.joeji.exchange.data.repository.DefaultExchangeRepository.Companion.BASE_CURRENCY_TYPE
import com.joeji.exchange.data.repository.DefaultExchangeRepository.Companion.LAST_REQUEST_TIME
import com.joeji.exchange.data.repository.util.FakePreferenceDataStore
import com.joeji.exchange.domain.model.CurrencyResponse
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import kotlin.test.assertEquals

@ExtendWith(MainCoroutineExtension::class)
class DefaultExchangeRepositoryTest {

    private lateinit var underTest: DefaultExchangeRepository
    private lateinit var remoteGateway: ExchangeRemoteGateway
    private lateinit var localGateway: ExchangeLocalGateway
    private lateinit var preferencesGateway: PreferencesGateway

    @BeforeEach
    fun setup() {
        remoteGateway = mockk()
        localGateway = mockk()
        preferencesGateway = FakePreferenceDataStore()

        underTest =
            DefaultExchangeRepository(
                remoteGateway = remoteGateway,
                localGateway = localGateway,
                preferencesGateway = preferencesGateway,
                ioDispatcher = StandardTestDispatcher()
            )
    }

    @Test
    fun `test that fetchCurrencies calls saveCurrencyList and updates lastRequestTime`() = runTest {
        val currencies = mockCurrencies()
        val baseCurrency = "NZD"
        val timestamp = 123456789L

        coEvery { remoteGateway.fetchCurrencyList() } returns Result.Success(
            CurrencyResponse(
                baseCurrency = baseCurrency,
                currencies = currencies
            )
        )

        coEvery { localGateway.saveCurrencyList(currencies) } returns mockk(relaxed = true)

        underTest.fetchCurrencies(fetchTimestamp = timestamp)

        coVerify { localGateway.saveCurrencyList(currencies) }

        val expectedTimestamp =
            preferencesGateway.monitorLong(LAST_REQUEST_TIME).first()

        assertEquals(expectedTimestamp, timestamp)

        val expectedBaseCurrency =
            preferencesGateway.monitorString(BASE_CURRENCY_TYPE, "")
                .first()

        assertEquals(expectedBaseCurrency, baseCurrency)
    }

    @Test
    fun `test that monitorLastRequestTime updates cachedLastRequestTime`() = runTest {
        val newTime = 123456789L
        preferencesGateway.putLong(LAST_REQUEST_TIME, newTime)

        delay(100)
        assertEquals(newTime, underTest.getLastRequestTime())
    }

    @Test
    fun `test that saveBaseCurrencyType calls preferencesGateway putString when forceUpdate is true`() =
        runTest {
            val baseCurrency = "USD"

            underTest.saveBaseCurrencyType(baseCurrency, true)

            assertEquals(
                baseCurrency,
                preferencesGateway.monitorString(BASE_CURRENCY_TYPE, "").first()
            )
        }

    @Test
    fun `test getBaseCurrencyType returns correct value`() = runTest {
        val baseCurrency = "USD"

        preferencesGateway.putString(BASE_CURRENCY_TYPE, baseCurrency)

        val result = underTest.getBaseCurrencyType()

        assertEquals(baseCurrency, result)
    }
}
