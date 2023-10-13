package com.openclassrooms.realestatemanager.data.local.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.openclassrooms.realestatemanager.domain.datastore.DataStoreRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreRepositoryImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>,
) : DataStoreRepository {

    companion object {
        const val DOLLAR_BOOLEAN_KEY = "dollar_boolean"
    }

    override suspend fun writeDollarBooleanToTrue() {
        val dataStoreKey = booleanPreferencesKey(DOLLAR_BOOLEAN_KEY)

        dataStore.edit { settings ->
            settings[dataStoreKey] = true
        }
    }

    override suspend fun writeDollarBooleanToFalse() {
        val dataStoreKey = booleanPreferencesKey(DOLLAR_BOOLEAN_KEY)

        dataStore.edit { settings ->
            settings[dataStoreKey] = false
        }
    }

    override fun readDollarBoolean(): Flow<Boolean> {
        val dataStoreKey = booleanPreferencesKey(DOLLAR_BOOLEAN_KEY)

        return dataStore.data.map { preferences ->
            preferences[dataStoreKey] ?: false
        }
    }
}