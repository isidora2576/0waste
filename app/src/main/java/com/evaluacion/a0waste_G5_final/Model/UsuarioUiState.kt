package com.evaluacion.a0waste_G5_final.Model
import com.evaluacion.a0waste_G5_final.Model.UsuarioErrores

data class UsuarioUiState(
    val nombre: String = "",
    val correo: String = "",
    val clave: String = "",
    val direccion: String = "",
    val aceptaTerminos: Boolean = false,
    val errores: UsuarioErrores = UsuarioErrores()
)