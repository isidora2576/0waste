package com.evaluacion.a0waste_G5_final.Model

data class PuntosRequest(
    val usuarioId: Long,
    val puntos: Int,
    val descripcion: String
)