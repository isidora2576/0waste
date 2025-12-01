package com.evaluacion.a0waste_G5_final.Service

import com.evaluacion.a0waste_G5_final.Model.LoginRequest
import com.evaluacion.a0waste_G5_final.Model.PuntosRequest
import com.evaluacion.a0waste_G5_final.Model.UsuarioRequest
import com.evaluacion.a0waste_G5_final.Model.UsuarioResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {

    // USUARIOS
    @POST("usuarios/registro")
    suspend fun registrarUsuario(@Body usuario: UsuarioRequest): Response<Any>

    @POST("usuarios/login")
    suspend fun loginUsuario(@Body login: LoginRequest): Response<Any>

    @GET("usuarios/{id}")
    suspend fun obtenerUsuario(@Path("id") id: Long): Response<Any>

    @GET("usuarios/por-email/{email}")
    suspend fun obtenerUsuarioPorEmail(@Path("email") email: String): Response<UsuarioResponse>

    // PUNTOS
    @POST("puntos/escanear")
    suspend fun agregarPuntos(@Body puntos: PuntosRequest): Response<Any>

    @POST("puntos/canjear")
    suspend fun canjearPuntos(@Body puntos: PuntosRequest): Response<Any>

    @GET("puntos/historial/{usuarioId}")
    suspend fun obtenerHistorialPuntos(@Path("usuarioId") usuarioId: Long): Response<Any>

}