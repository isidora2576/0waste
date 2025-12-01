package com.evaluacion.a0waste_G5_final.Model

data class UsuarioRequest(
    val nombreCompleto: String,
    val email: String,
    val password: String,
    val direccion: String,
    val telefono: String,
    val tipoReciclador: String,
    val materialesInteres: List<String>,
    val aceptaTerminos: Boolean,
    val permisoCamara: Boolean
) {
    fun toApiFormat(): UsuarioRequest {
        return this.copy(
            tipoReciclador = when (this.tipoReciclador.uppercase()) {
                "PRINCIPIANTE" -> "PRINCIPIANTE"
                "INTERMEDIO" -> "INTERMEDIO"
                "AVANZADO" -> "AVANZADO"
                "EXPERTO" -> "AVANZADO"
                else -> "PRINCIPIANTE" // Valor por defecto
            }
        )
    }
}