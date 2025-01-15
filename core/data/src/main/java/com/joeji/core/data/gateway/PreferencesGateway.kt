package com.joeji.core.data.gateway

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferencesGateway {

    suspend fun putString(key: String, value: String)

    suspend fun putStringSet(key: String, value: Set<String>)

    suspend fun putInt(key: String, value: Int)

    suspend fun putLong(key: String, value: Long)

    suspend fun putFloat(key: String, value: Float)

    suspend fun putBoolean(key: String, value: Boolean)

    fun monitorPreferences(): Flow<Preferences>

    fun monitorString(key: String, defaultValue: String?): Flow<String?>

    fun monitorStringSet(key: String, defaultValue: MutableSet<String>?): Flow<MutableSet<String>?>

    fun monitorInt(key: String, defaultValue: Int): Flow<Int>

    fun monitorLong(key: String, defaultValue: Long): Flow<Long>

    fun monitorFloat(key: String, defaultValue: Float): Flow<Float>

    fun monitorBoolean(key: String, defaultValue: Boolean): Flow<Boolean>

    fun monitorLong(key: String): Flow<Long?>

    fun monitorBoolean(key: String): Flow<Boolean?>

    suspend fun removeByKey(key: String)

    suspend fun removeByKeys(keys: Array<String>)
}