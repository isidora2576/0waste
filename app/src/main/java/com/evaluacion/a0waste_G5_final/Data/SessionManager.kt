package com.evaluacion.a0waste_G5_final.Data

import android.content.Context
import android.content.SharedPreferences
import kotlin.math.absoluteValue

class SessionManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("0waste_session", Context.MODE_PRIVATE)

    companion object {
        private const val KEY_USER_ID = "user_id"
        private const val KEY_USER_EMAIL = "user_email"
        private const val KEY_USER_NAME = "user_name"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"

        private const val KEY_ACEPTO_CAMARA = "acepto_camara"
    }

    // GUARDAR SESIÓN COMPLETA
    fun saveUserSession(userId: Long, email: String, name: String) {
        sharedPreferences.edit().apply {
            putLong(KEY_USER_ID, userId)
            putString(KEY_USER_EMAIL, email)
            putString(KEY_USER_NAME, name)
            putBoolean(KEY_IS_LOGGED_IN, true)
            apply()
        }
        println("Sesión guardada - ID: $userId, Email: $email")
    }

    // GUARDAR SI ACEPTÓ CÁMARA
    fun saveAceptoCamara(acepto: Boolean) {
        sharedPreferences.edit().putBoolean(KEY_ACEPTO_CAMARA, acepto).apply()
        println("Permiso cámara guardado: $acepto")
    }

    // OBTENER SI ACEPTÓ CÁMARA
    fun getAceptoCamara(): Boolean {
        return sharedPreferences.getBoolean(KEY_ACEPTO_CAMARA, false)
    }


    // OBTENER ID DEL USUARIO
    fun getUserId(): Long {
        val userId = sharedPreferences.getLong(KEY_USER_ID, 0L)
        println("OBTENIENDO USER ID: $userId")
        return userId
    }

    // OBTENER EMAIL
    fun getUserEmail(): String {
        return sharedPreferences.getString(KEY_USER_EMAIL, "") ?: ""
    }

    // OBTENER NOMBRE
    fun getUserName(): String {
        return sharedPreferences.getString(KEY_USER_NAME, "") ?: ""
    }

    // VERIFICAR SESIÓN
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false) && getUserId() > 0L
    }

    // CERRAR SESIÓN
    fun logout() {
        sharedPreferences.edit().clear().apply()
        println("Sesión cerrada")
    }

    //Generar ID desde email
    private fun generarUserIdDesdeEmail(email: String): Long {
        return email.hashCode().toLong().absoluteValue
    }

    fun clearCache() {
        sharedPreferences.edit().clear().apply()
        println("🧹 CACHE LIMPIADO")
    }
    fun printSessionState() {
        val userId = sharedPreferences.getLong(KEY_USER_ID, 0L)
        val email = sharedPreferences.getString(KEY_USER_EMAIL, "")
        val isLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)

        println("📊 ESTADO DE SESIÓN:")
        println("   - User ID: $userId")
        println("   - Email: $email")
        println("   - Logged In: $isLoggedIn")
    }
}