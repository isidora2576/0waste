package com.evaluacion.a0waste_G5_final.Viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.test.core.app.ApplicationProvider
import com.evaluacion.a0waste_G5_final.Data.SessionManager
import com.evaluacion.a0waste_G5_final.Model.PuntosRequest
import com.evaluacion.a0waste_G5_final.Service.RetrofitClient
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

open class WasteViewModel(application: Application) : AndroidViewModel(application) {

    private val sessionManager = SessionManager(application.applicationContext)

    // PUNTOS DESDE API
    val _puntosUsuario = MutableStateFlow<Int>(0)
    val puntosUsuario: StateFlow<Int> = _puntosUsuario.asStateFlow()

    private val _cargando = MutableStateFlow(false)
    val cargando: StateFlow<Boolean> = _cargando

    // MOSTRAR MENSAJE DE PUNTOS (Para ScanScreen, RewardsScreen, etc.)
    val _mostrarMensajePuntos = MutableStateFlow(false)
    val mostrarMensajePuntos: StateFlow<Boolean> = _mostrarMensajePuntos

    val _mensajePuntos = MutableStateFlow("")
    val mensajePuntos: StateFlow<String> = _mensajePuntos.asStateFlow()

    // CARGAR PUNTOS DESDE API
    fun cargarPuntosDesdeApi() {
        viewModelScope.launch {
            val usuarioId = sessionManager.getUserId()
            if (usuarioId > 0L) {
                _cargando.value = true

                try {
                    println("WasteViewModel - Cargando puntos para usuario: $usuarioId")
                    val response = RetrofitClient.apiService.obtenerUsuario(usuarioId)

                    if (response.isSuccessful) {
                        val usuario = response.body() as? Map<String, Any>
                        val puntos = (usuario?.get("puntosActuales") as? Double)?.toInt() ?: 0

                        _puntosUsuario.value = puntos
                        println("Puntos cargados: $puntos")
                    } else {
                        mostrarMensaje("Error al cargar puntos")
                    }
                } catch (e: Exception) {
                    mostrarMensaje("Error de conexión")
                    println("Error: ${e.message}")
                } finally {
                    _cargando.value = false
                }
            }
        }
    }

    // AGREGAR PUNTOS VIA API
    fun agregarPuntosApi(puntos: Int, descripcion: String) {
        viewModelScope.launch {
            val usuarioId = sessionManager.getUserId()
            if (usuarioId > 0L) {
                try {
                    println("Agregando $puntos puntos para usuario: $usuarioId")
                    val response = RetrofitClient.apiService.agregarPuntos(
                        PuntosRequest(usuarioId, puntos, descripcion)
                    )

                    if (response.isSuccessful) {
                        mostrarMensaje("+$puntos puntos agregados")
                        // Recargar puntos actualizados
                        cargarPuntosDesdeApi()
                    } else {
                        mostrarMensaje("Error al agregar puntos")
                    }
                } catch (e: Exception) {
                    mostrarMensaje("Error de conexión")
                    println("Error: ${e.message}")
                }
            } else {
                mostrarMensaje("⚠Inicia sesión para guardar puntos")
            }
        }
    }

    // CANJEAR PUNTOS VIA API
    fun canjearPuntosApi(puntos: Int, descripcion: String) {
        viewModelScope.launch {
            val usuarioId = sessionManager.getUserId()
            if (usuarioId > 0L) {
                try {
                    println("Canjeando $puntos puntos para usuario: $usuarioId")
                    val response = RetrofitClient.apiService.canjearPuntos(
                        PuntosRequest(usuarioId, puntos, descripcion)
                    )

                    if (response.isSuccessful) {
                        mostrarMensaje("$puntos puntos canjeados")
                        // Recargar puntos actualizados
                        cargarPuntosDesdeApi()
                    } else {
                        mostrarMensaje("Error al canjear puntos")
                    }
                } catch (e: Exception) {
                    mostrarMensaje("Error de conexión")
                    println("Error: ${e.message}")
                }
            }
        }
    }

    // MOSTRAR MENSAJE TEMPORAL
    private fun mostrarMensaje(mensaje: String) {
        viewModelScope.launch {
            _mensajePuntos.value = mensaje
            _mostrarMensajePuntos.value = true

            // Ocultar mensaje después de 3 segundos
            delay(3000)
            _mostrarMensajePuntos.value = false
        }
    }

    fun getPoints(): Int = _puntosUsuario.value

    fun obtenerUserId(): Long = sessionManager.getUserId()

    fun hayUsuarioLogueado(): Boolean = sessionManager.isLoggedIn()
}

class PreviewWasteViewModel : WasteViewModel(
    application = ApplicationProvider.getApplicationContext() as Application
) {
    init {
        viewModelScope.launch {
            _puntosUsuario.value = 125
            _mostrarMensajePuntos.value = true
            _mensajePuntos.value = "+5 puntos agregados"
        }
    }
}