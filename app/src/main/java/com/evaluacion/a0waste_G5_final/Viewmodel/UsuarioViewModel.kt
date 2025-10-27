package com.evaluacion.a0waste_G5_final.Viewmodel

import androidx.lifecycle.ViewModel
import com.evaluacion.a0waste_G5_final.Model.UsuarioErrores
import com.evaluacion.a0waste_G5_final.Model.UsuarioUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class UsuarioViewModel : ViewModel() {

    private val _estado = MutableStateFlow(UsuarioUiState())
    val estado: StateFlow<UsuarioUiState> = _estado.asStateFlow()

    // Lista de materiales reciclables
    val materialesDisponibles = listOf(
        "Plástico PET", "Vidrio", "Aluminio", "Cartón",
        "Papel", "Tetrapak", "Electrónicos", "Pilas"
    )

    // Tipos de reciclador
    val tiposReciclador = listOf("Principiante", "Intermedio", "Avanzado")

    // Funciones existentes
    fun onNombreChange(valor: String) {
        _estado.update { it.copy(nombre = valor, errores = it.errores.copy(nombre = null)) }
    }

    fun onCorreoChange(valor: String) {
        _estado.update { it.copy(correo = valor, errores = it.errores.copy(correo = null)) }
    }

    fun onClaveChange(valor: String) {
        _estado.update { it.copy(clave = valor, errores = it.errores.copy(clave = null)) }
    }

    fun onDireccionChange(valor: String) {
        _estado.update { it.copy(direccion = valor, errores = it.errores.copy(direccion = null)) }
    }

    fun onTelefonoChange(valor: String) {
        _estado.update { it.copy(telefono = valor, errores = it.errores.copy(telefono = null)) }
    }

    fun onTipoRecicladorChange(valor: String) {
        _estado.update { it.copy(tipoReciclador = valor, errores = it.errores.copy(tipoReciclador = null)) }
    }

    fun onMaterialInteresChange(material: String, seleccionado: Boolean) {
        val nuevosMateriales = if (seleccionado) {
            _estado.value.materialesInteres + material
        } else {
            _estado.value.materialesInteres - material
        }
        _estado.update { it.copy(materialesInteres = nuevosMateriales) }
    }

    fun onAceptarTerminosChange(valor: Boolean) {
        _estado.update { it.copy(aceptaTerminos = valor) }
    }

    fun onAceptaCamaraChange(valor: Boolean) {
        _estado.update { it.copy(aceptaCamara = valor, errores = it.errores.copy(aceptaCamara = null)) }
    }

    // Validación mejorada
    fun validarFormulario(): Boolean {
        val estadoActual = _estado.value
        val errores = UsuarioErrores(
            nombre = if (estadoActual.nombre.isBlank()) "Campo obligatorio" else null,
            correo = if (!estadoActual.correo.contains("@")) "Correo inválido" else null,
            clave = if (estadoActual.clave.length < 6) "Debe tener al menos 6 caracteres" else null,
            direccion = if (estadoActual.direccion.isBlank()) "Campo obligatorio" else null,
            telefono = if (estadoActual.telefono.length < 9) "Teléfono inválido" else null,
            tipoReciclador = if (estadoActual.tipoReciclador.isBlank()) "Selecciona tu nivel" else null,
            aceptaCamara = if (!estadoActual.aceptaCamara) "Necesario para subir evidencia" else null
        )

        val hayErrores = listOfNotNull(
            errores.nombre, errores.correo, errores.clave,
            errores.direccion, errores.telefono,
            errores.tipoReciclador, errores.aceptaCamara
        ).isNotEmpty()

        _estado.update { it.copy(errores = errores) }
        return !hayErrores
    }
}