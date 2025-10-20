package com.evaluacion.a0waste_G5_final.Data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "preferencias_0waste")

class EstadoDataStore(private val context: Context) {

    private val MODO_RECICLAJE_ACTIVADO = booleanPreferencesKey("modo_reciclaje_activado")

    suspend fun guardarEstado(valor: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[MODO_RECICLAJE_ACTIVADO] = valor
        }
    }

    fun obtenerEstado(): Flow<Boolean?> {
        return context.dataStore.data.map { preferences ->
            preferences[MODO_RECICLAJE_ACTIVADO]
        }
    }
}