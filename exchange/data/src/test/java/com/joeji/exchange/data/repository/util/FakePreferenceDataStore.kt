package com.joeji.exchange.data.repository.util

import com.joeji.core.data.gateway.PreferencesGateway
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf

class FakePreferenceDataStore : PreferencesGateway {

    private val preferences = hashMapOf<Any, Any>()

    private val timestampFlow = MutableStateFlow<Long?>(null)

    override suspend fun putString(key: String, value: String) {
        preferences[key] = value
    }

    override suspend fun putStringSet(key: String, value: Set<String>) {
        preferences[key] = value
    }

    override suspend fun putInt(key: String, value: Int) {
        preferences[key] = value
    }

    override suspend fun putLong(key: String, value: Long) {
        preferences[key] = value
        timestampFlow.value = value
    }

    override suspend fun putFloat(key: String, value: Float) {
        preferences[key] = value
    }

    override suspend fun putBoolean(key: String, value: Boolean) {
        preferences[key] = value
    }

    override fun monitorString(key: String, defaultValue: String?): Flow<String?> {
        return flowOf(preferences[key] as? String ?: defaultValue)
    }

    override fun monitorStringSet(
        key: String,
        defaultValue: MutableSet<String>?
    ): Flow<MutableSet<String>?> {
        return flowOf(null)
    }

    override fun monitorInt(key: String, defaultValue: Int): Flow<Int> {
        return flowOf(preferences[key] as? Int ?: defaultValue)
    }

    override fun monitorLong(key: String, defaultValue: Long): Flow<Long> {
        return flowOf(preferences[key] as? Long ?: defaultValue)
    }

    override fun monitorLong(key: String): Flow<Long?> {
        return timestampFlow
    }

    override fun monitorFloat(key: String, defaultValue: Float): Flow<Float> {
        return flowOf(preferences[key] as? Float ?: defaultValue)
    }

    override fun monitorBoolean(key: String, defaultValue: Boolean): Flow<Boolean> {
        return flowOf(preferences[key] as? Boolean ?: defaultValue)
    }

    override fun monitorBoolean(key: String): Flow<Boolean?> {
        return flowOf(preferences[key] as? Boolean)
    }

    override suspend fun removeByKey(key: String) {
        preferences.remove(key)
    }

    override suspend fun removeByKeys(keys: Array<String>) {
        keys.forEach { key ->
            preferences.remove(key)
        }
    }

    fun clear() {
        preferences.clear()
    }
}