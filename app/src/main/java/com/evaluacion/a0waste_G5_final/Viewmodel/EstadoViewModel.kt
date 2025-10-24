package com.evaluacion.a0waste_G5_final.Viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.evaluacion.a0waste_G5_final.Data.EstadoDataStore
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class EstadoViewModel(application: Application) : AndroidViewModel(application) {

    private val dataStore = EstadoDataStore(application.applicationContext)


    private val _activo = MutableStateFlow<Boolean?>(value = null)
    val activo: StateFlow<Boolean?> = _activo


    private val _mostrarMensaje = MutableStateFlow(value = false)
    val mostrarMensaje: StateFlow<Boolean> = _mostrarMensaje

    init {
        cargarEstado()
    }

    fun cargarEstado() {
        viewModelScope.launch {
            // Simula demora para mostrar loader (como en la guía)
            delay(timeMillis = 1500)
            _activo.value = dataStore.obtenerEstado().first() ?: false
        }
    }

    fun alternarEstado() {
        viewModelScope.launch {

            val nuevoValor = !(_activo.value ?: false)


            dataStore.guardarEstado(nuevoValor)


            _activo.value = nuevoValor


            _mostrarMensaje.value = true

            delay(timeMillis = 2000)
            _mostrarMensaje.value = false
        }
    }
}