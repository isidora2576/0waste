package com.evaluacion.a0waste_G5_final.Viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import androidx.test.core.app.ApplicationProvider
import com.evaluacion.a0waste_G5_final.Data.AppDataStore
import com.evaluacion.a0waste_G5_final.Navigation.NavigationEvent
import com.evaluacion.a0waste_G5_final.Navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

open class WasteViewModel(application: Application) : AndroidViewModel(application) {

    private val appDataStore = AppDataStore(application.applicationContext)

    val _puntosUsuario = MutableStateFlow<Int?>(null)
    val puntosUsuario: StateFlow<Int?> = _puntosUsuario

    private val _mostrarMensajePuntos = MutableStateFlow(false)
    val mostrarMensajePuntos: StateFlow<Boolean> = _mostrarMensajePuntos

    init {
        cargarPuntos()
    }

    private fun cargarPuntos() {
        viewModelScope.launch {
            delay(1500)
            _puntosUsuario.value = appDataStore.getUserPoints().first()
        }
    }


    fun agregarPuntos(puntos: Int) {
        viewModelScope.launch {
            appDataStore.addUserPoints(puntos)

            _puntosUsuario.value = appDataStore.getUserPoints().first()

            _mostrarMensajePuntos.value = true

            delay(2000)

            _mostrarMensajePuntos.value = false
        }
    }

    fun getPoints(): Int = _puntosUsuario.value ?: 0

    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    fun navigateTo(screen: Screen) {
        CoroutineScope(context = Dispatchers.Main).launch {
            _navigationEvents.emit(NavigationEvent.NavigateTo(route = screen))
        }
    }

    fun navigateBack() {
        CoroutineScope(context = Dispatchers.Main).launch {
            _navigationEvents.emit(NavigationEvent.PopBackStack)
        }
    }

    fun navigateUp() {
        CoroutineScope(context = Dispatchers.Main).launch {
            _navigationEvents.emit(value = NavigationEvent.NavigateUp)
        }
    }
}


class PreviewWasteViewModel : WasteViewModel(
    application = ApplicationProvider.getApplicationContext() as Application
) {
    init {

        viewModelScope.launch {
            _puntosUsuario.value = 0
        }
    }
}