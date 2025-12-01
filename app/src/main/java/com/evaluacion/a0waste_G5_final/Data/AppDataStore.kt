package com.evaluacion.a0waste_G5_final.Data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.stringSetPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.appDataStore: DataStore<Preferences> by preferencesDataStore(name = "app_settings")

class AppDataStore(private val context: Context) {

    companion object {

        val ECO_MODE_ACTIVE = booleanPreferencesKey("eco_mode_active")

    }

    // Modo Eco

    suspend fun setEcoMode(active: Boolean) {
        context.appDataStore.edit { preferences ->
            preferences[ECO_MODE_ACTIVE] = active
        }
    }

    fun getEcoMode(): Flow<Boolean> = context.appDataStore.data.map {
        it[ECO_MODE_ACTIVE] ?: false
    }
}
