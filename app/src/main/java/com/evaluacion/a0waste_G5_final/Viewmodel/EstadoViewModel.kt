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

    // Estado que representa si está "activado" o no
    private val _activo = MutableStateFlow<Boolean?>(value = null)
    val activo: StateFlow<Boolean?> = _activo

    // Estado para mostrar u ocultar el mensaje animado
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
            // Alternamos el valor actual
            val nuevoValor = !(_activo.value ?: false)

            // Guardamos en DataStore
            dataStore.guardarEstado(nuevoValor)

            // Actualizamos el flujo
            _activo.value = nuevoValor

            // Mostramos el mensaje visual animado
            _mostrarMensaje.value = true

            delay(timeMillis = 2000) // Ocultamos después de 2 segundos
            _mostrarMensaje.value = false
        }
    }
}