package com.evaluacion.a0waste_G5_final.Data

import android.content.Context
import kotlinx.coroutines.flow.Flow

class EstadoDataStore(private val context: Context) {

    private val appDataStore = AppDataStore(context)

    suspend fun guardarEstado(valor: Boolean) {
        appDataStore.setEcoMode(valor)
    }

    fun obtenerEstado(): Flow<Boolean> {
        return appDataStore.getEcoMode()
    }
}