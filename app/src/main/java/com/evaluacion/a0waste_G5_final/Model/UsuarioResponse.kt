package com.evaluacion.a0waste_G5_final.Model

data class UsuarioResponse(
    val id: Long,
    val nombreCompleto: String,
    val email: String,
    val direccion: String,
    val telefono: String,
    val tipoReciclador: String,
    val materialesInteres: List<String>,
    val permisoCamara: Boolean,
    val puntosActuales: Int
)