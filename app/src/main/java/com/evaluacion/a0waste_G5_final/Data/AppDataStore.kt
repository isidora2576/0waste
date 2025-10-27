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


        val USER_POINTS = intPreferencesKey("user_points")


        val DEMO_USER_EMAIL = stringPreferencesKey("demo_user_email")
        val DEMO_USER_NAME = stringPreferencesKey("demo_user_name")
        val DEMO_USER_LOGGED_IN = booleanPreferencesKey("demo_user_logged_in")
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

    // Puntos Usuario

    suspend fun saveUserPoints(points: Int) {
        context.appDataStore.edit { preferences ->
            preferences[USER_POINTS] = points
        }
    }

    fun getUserPoints(): Flow<Int> = context.appDataStore.data.map {
        it[USER_POINTS] ?: 0
    }

    suspend fun addUserPoints(pointsToAdd: Int) {
        context.appDataStore.edit { preferences ->
            val currentPoints = preferences[USER_POINTS] ?: 0
            preferences[USER_POINTS] = currentPoints + pointsToAdd
        }
    }

    // Usuario Demo

    suspend fun saveDemoUser(email: String, name: String) {
        context.appDataStore.edit { preferences ->
            preferences[DEMO_USER_EMAIL] = email
            preferences[DEMO_USER_NAME] = name
            preferences[DEMO_USER_LOGGED_IN] = true
        }
    }

    fun getDemoUserEmail(): Flow<String?> = context.appDataStore.data.map {
        it[DEMO_USER_EMAIL]
    }

    fun getDemoUserName(): Flow<String?> = context.appDataStore.data.map {
        it[DEMO_USER_NAME]
    }

    fun isDemoUserLoggedIn(): Flow<Boolean> = context.appDataStore.data.map {
        it[DEMO_USER_LOGGED_IN] ?: false
    }

    suspend fun logoutDemoUser() {
        context.appDataStore.edit { preferences ->
            preferences[DEMO_USER_LOGGED_IN] = false
        }
    }

    // Limpiar todos los datos
    suspend fun clearAllData() {
        context.appDataStore.edit { preferences ->
            preferences.clear()
        }
    }
}