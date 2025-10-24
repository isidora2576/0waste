package com.evaluacion.a0waste_G5_final.Model
import com.evaluacion.a0waste_G5_final.Model.UsuarioErrores

data class UsuarioUiState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val direccion: String = "",
    val telefono: String = "",
    val tipoReciclador: String = "", // "Principiante", "Intermedio", "Avanzado"
    val materialesInteres: List<String> = emptyList(),
    val aceptaTerminos: Boolean = false,
    val aceptaCamara: Boolean = false, //Pérmiso para usar cámara
    val errores: UsuarioErrores = UsuarioErrores()
)