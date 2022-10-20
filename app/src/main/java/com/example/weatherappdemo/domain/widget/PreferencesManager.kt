package com.example.weatherappdemo.domain.widget

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import com.example.weatherappdemo.domain.util.Constants.DataStoreConstants.CURRENT_LATITUDE
import com.example.weatherappdemo.domain.util.Constants.DataStoreConstants.CURRENT_LONGITUDE
import com.example.weatherappdemo.domain.util.Constants.DataStoreConstants.DEFAULT_LAT
import com.example.weatherappdemo.domain.util.Constants.DataStoreConstants.DEFAULT_LONG
import com.example.weatherappdemo.domain.util.Constants.DataStoreConstants.PREF_NAME
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

class PreferencesManager(
    private val application: Application
) {
    private companion object {
        val Context.pref: DataStore<Preferences> by preferencesDataStore(
            name = PREF_NAME
        )
    }

    val latPref = application.applicationContext.pref.data
        .catch { e ->
            if (e is IOException)
                emit(emptyPreferences())
            else
                throw e
        }.map { preferences ->
            val lat = preferences[doublePreferencesKey(CURRENT_LATITUDE)] ?: DEFAULT_LAT
            lat
        }

    val longPref = application.applicationContext.pref.data
        .catch { e ->
            if (e is IOException)
                emit(emptyPreferences())
            else
                throw e
        }.map { preferences ->
            val long = preferences[doublePreferencesKey(CURRENT_LONGITUDE)] ?: DEFAULT_LONG
            long
        }

    suspend fun setCurrentLocation(latitude: Double, longitude: Double) {
        application.applicationContext.pref.edit { preferences ->
            preferences[doublePreferencesKey(CURRENT_LATITUDE)] = latitude
            preferences[doublePreferencesKey(CURRENT_LONGITUDE)] = longitude
        }
    }
}