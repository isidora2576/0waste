package com.evaluacion.a0waste_G5_final.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evaluacion.a0waste_G5_final.Data.SessionManager
import com.evaluacion.a0waste_G5_final.Model.LoginRequest
import com.evaluacion.a0waste_G5_final.Model.PuntosRequest
import com.evaluacion.a0waste_G5_final.Model.UsuarioRequest
import com.evaluacion.a0waste_G5_final.Model.UsuarioResponse
import com.evaluacion.a0waste_G5_final.Service.RetrofitClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class UsuarioViewModel : ViewModel() {

    private val _registroState = MutableStateFlow<Result<Any>?>(null)
    val registroState: StateFlow<Result<Any>?> = _registroState

    private val _loginState = MutableStateFlow<Result<Any>?>(null)
    val loginState: StateFlow<Result<Any>?> = _loginState

    private val _puntosState = MutableStateFlow<Result<Any>?>(null)
    val puntosState: StateFlow<Result<Any>?> = _puntosState

    // Estados para validaciones locales
    private val _erroresRegistro = MutableStateFlow<Map<String, String>>(emptyMap())
    val erroresRegistro: StateFlow<Map<String, String>> = _erroresRegistro

    private val _erroresLogin = MutableStateFlow<Map<String, String>>(emptyMap())
    val erroresLogin: StateFlow<Map<String, String>> = _erroresLogin
    private val _usuarioRegistrado = MutableStateFlow<UsuarioRequest?>(null)
    val usuarioRegistrado: StateFlow<UsuarioRequest?> = _usuarioRegistrado

    private val _usuarioData = MutableStateFlow<UsuarioResponse?>(null)
    val usuarioData: StateFlow<UsuarioResponse?> = _usuarioData

    private val _puntosUsuario = MutableStateFlow<Int>(0)
    val puntosUsuario: StateFlow<Int> = _puntosUsuario

    private val _loadingUsuario = MutableStateFlow(false)
    val loadingUsuario: StateFlow<Boolean> = _loadingUsuario

    // Validación local de registro
    fun validarRegistroLocal(
        nombre: String,
        email: String,
        password: String,
        direccion: String,
        telefono: String,
        tipoReciclador: String,
        aceptaTerminos: Boolean,
        aceptaCamara: Boolean
    ): Boolean {
        val errores = mutableMapOf<String, String>()

        if (nombre.isBlank()) errores["nombre"] = "Nombre es obligatorio"
        if (email.isBlank()) errores["email"] = "Email es obligatorio"
        else if (!email.contains("@")) errores["email"] = "Email inválido"
        if (password.isBlank()) errores["password"] = "Contraseña es obligatoria"
        else if (password.length < 6) errores["password"] = "Mínimo 6 caracteres"
        if (direccion.isBlank()) errores["direccion"] = "Dirección es obligatoria"
        if (telefono.isBlank()) errores["telefono"] = "Teléfono es obligatorio"
        else if (telefono.length < 9) errores["telefono"] = "Teléfono inválido"
        if (tipoReciclador.isBlank()) errores["tipoReciclador"] = "Selecciona tu nivel"
        if (!aceptaTerminos) errores["terminos"] = "Debes aceptar los términos"
        if (!aceptaCamara) errores["camara"] = "Necesario para subir evidencia"

        _erroresRegistro.value = errores
        return errores.isEmpty()
    }

    // Validación local de login
    fun validarLoginLocal(email: String, password: String): Boolean {
        val errores = mutableMapOf<String, String>()

        if (email.isBlank()) errores["email"] = "Email es obligatorio"
        else if (!email.contains("@")) errores["email"] = "Email inválido"
        if (password.isBlank()) errores["password"] = "Contraseña es obligatoria"
        else if (password.length < 6) errores["password"] = "Mínimo 6 caracteres"

        _erroresLogin.value = errores
        return errores.isEmpty()
    }

    // Limpiar errores
    fun limpiarErroresRegistro() {
        _erroresRegistro.value = emptyMap()
    }

    fun limpiarErroresLogin() {
        _erroresLogin.value = emptyMap()
    }


    fun registrarUsuario(usuario: UsuarioRequest) {
        viewModelScope.launch {
            try {
                println("Intentando registrar usuario: ${usuario.email}")

                // CONVERTIR A FORMATO API ANTES DE ENVIAR
                val usuarioParaApi = usuario.toApiFormat()
                println("Datos convertidos para API: $usuarioParaApi")

                val response = RetrofitClient.apiService.registrarUsuario(usuarioParaApi)

                if (response.isSuccessful) {
                    println("Usuario registrado exitosamente")


                    _registroState.value = Result.success(response.body()!!)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Error desconocido"
                    println("Error en registro: ${response.code()} - $errorBody")
                    _registroState.value = Result.failure(Exception("Error del servidor: $errorBody"))
                }
            } catch (e: Exception) {
                println("Excepción en registro: ${e.message}")
                _registroState.value = Result.failure(Exception("Error de conexión: ${e.message}"))

            }
        }
    }


    private lateinit var sessionManager: SessionManager

    fun setSessionManager(manager: SessionManager) {
        this.sessionManager = manager
    }

    fun loginUsuario(email: String, password: String) {
        viewModelScope.launch {
            try {
                println("Intentando login: $email")

                if (sessionManager == null) {
                    println("SessionManager no inicializado")
                    return@launch
                }

                val loginResponse = RetrofitClient.apiService.loginUsuario(LoginRequest(email, password))

                if (loginResponse.isSuccessful) {
                    println("Login exitoso, obteniendo datos del usuario...")

                    val usuarioResponse = RetrofitClient.apiService.obtenerUsuarioPorEmail(email)

                    if (usuarioResponse.isSuccessful) {
                        val usuario = usuarioResponse.body()
                        if (usuario != null) {
                            // VERIFICAR QUÉ ID ESTÁ LLEGANDO DE LA API
                            println(" USUARIO OBTENIDO DE LA API:")
                            println("   - ID: ${usuario.id}")
                            println("   - Email: ${usuario.email}")
                            println("   - Nombre: ${usuario.nombreCompleto}")

                            sessionManager!!.saveUserSession(
                                userId = usuario.id,
                                email = usuario.email,
                                name = usuario.nombreCompleto
                            )

                            sessionManager!!.printSessionState()

                            _loginState.value = Result.success(usuario)
                        } else {
                            _loginState.value = Result.failure(Exception("No se pudieron obtener los datos del usuario"))
                        }
                    } else {
                        println(" Error al obtener usuario: ${usuarioResponse.code()}")
                        _loginState.value = Result.failure(Exception("Error al obtener datos del usuario"))
                    }
                } else {
                    val errorBody = loginResponse.errorBody()?.string() ?: "Credenciales inválidas"
                    println("Error en login: ${loginResponse.code()} - $errorBody")
                    _loginState.value = Result.failure(Exception(errorBody))
                }
            } catch (e: Exception) {
                println("Excepción en login: ${e.message}")
                _loginState.value = Result.failure(Exception("Error de conexión: ${e.message}"))
            }
        }
    }

    fun agregarPuntosApi(usuarioId: Long, puntos: Int, descripcion: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.agregarPuntos(
                    PuntosRequest(usuarioId, puntos, descripcion)
                )
                if (response.isSuccessful) {
                    _puntosState.value = Result.success(response.body()!!)
                } else {
                    _puntosState.value = Result.failure(Exception("Error al agregar puntos: ${response.code()}"))
                }
            } catch (e: Exception) {
                _puntosState.value = Result.failure(e)
            }
        }
    }

    fun canjearPuntosApi(usuarioId: Long, puntos: Int, descripcion: String) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.canjearPuntos(
                    PuntosRequest(usuarioId, puntos, descripcion)
                )
                if (response.isSuccessful) {
                    _puntosState.value = Result.success(response.body()!!)
                } else {
                    _puntosState.value = Result.failure(Exception("Error al canjear puntos: ${response.code()}"))
                }
            } catch (e: Exception) {
                _puntosState.value = Result.failure(e)
            }
        }
    }
    fun obtenerUsuarioDesdeApi(usuarioId: Long) {
        viewModelScope.launch {
            try {
                _loadingUsuario.value = true
                println("Obteniendo usuario ID: $usuarioId desde API...")

                val response = RetrofitClient.apiService.obtenerUsuario(usuarioId)

                if (response.isSuccessful) {
                    val usuario = response.body() as? Map<String, Any>
                    if (usuario != null) {
                        // Parsear datos del usuario
                        val usuarioResponse = UsuarioResponse(
                            id = (usuario["id"] as? Double)?.toLong() ?: usuarioId,
                            nombreCompleto = usuario["nombreCompleto"] as? String ?: "",
                            email = usuario["email"] as? String ?: "",
                            direccion = usuario["direccion"] as? String ?: "",
                            telefono = usuario["telefono"] as? String ?: "",
                            tipoReciclador = usuario["tipoReciclador"] as? String ?: "Principiante",
                            materialesInteres = usuario["materialesInteres"] as? List<String> ?: emptyList(),
                            permisoCamara = usuario["permisoCamara"] as? Boolean ?: false,
                            puntosActuales = (usuario["puntosActuales"] as? Double)?.toInt() ?: 0
                        )

                        _usuarioData.value = usuarioResponse
                        _puntosUsuario.value = usuarioResponse.puntosActuales

                        println("Usuario obtenido: ${usuarioResponse.nombreCompleto}, Puntos: ${usuarioResponse.puntosActuales}")
                    }
                } else {
                    println("Error al obtener usuario: ${response.code()}")
                    // Intentar obtener por email como fallback
                    obtenerUsuarioPorEmailFallback()
                }
            } catch (e: Exception) {
                println("Excepción al obtener usuario: ${e.message}")
            } finally {
                _loadingUsuario.value = false
            }
        }
    }
    private fun obtenerUsuarioPorEmailFallback() {
        viewModelScope.launch {
            try {
                val email = sessionManager.getUserEmail()
                if (email.isNotEmpty()) {
                    println("Intentando obtener usuario por email: $email")

                    val response = RetrofitClient.apiService.obtenerUsuarioPorEmail(email)
                    if (response.isSuccessful) {
                        val usuario = response.body()
                        if (usuario != null) {
                            _usuarioData.value = usuario
                            _puntosUsuario.value = usuario.puntosActuales

                            // Actualizar SessionManager con el ID correcto
                            sessionManager.saveUserSession(
                                userId = usuario.id,
                                email = usuario.email,
                                name = usuario.nombreCompleto
                            )
                        }
                    }
                }
            } catch (e: Exception) {
                println("Fallback también falló: ${e.message}")
            }
        }
    }
    private val _historialPuntos = MutableStateFlow<List<Any>>(emptyList())
    val historialPuntos: StateFlow<List<Any>> = _historialPuntos

    fun obtenerHistorialPuntos(usuarioId: Long) {
        viewModelScope.launch {
            try {
                val response = RetrofitClient.apiService.obtenerHistorialPuntos(usuarioId)
                if (response.isSuccessful) {
                    val historial = response.body() as? List<*> ?: emptyList<Any>()
                    _historialPuntos.value = historial.filterNotNull()
                }
            } catch (e: Exception) {
                println("Error obteniendo historial: ${e.message}")
            }
        }
        fun refrescarDatosUsuario() {
            val userId = sessionManager.getUserId()
            if (userId > 0L) {
                obtenerUsuarioDesdeApi(userId)
                obtenerHistorialPuntos(userId)
            }
        }
        fun cargarPuntosUsuario(usuarioId: Long) {
            viewModelScope.launch {
                try {
                    _loadingUsuario.value = true
                    val response = RetrofitClient.apiService.obtenerUsuario(usuarioId)

                    if (response.isSuccessful) {
                        val usuario = response.body() as? Map<String, Any>
                        if (usuario != null) {
                            val puntos = (usuario["puntosActuales"] as? Double)?.toInt() ?: 0
                            _puntosUsuario.value = puntos
                            println("Puntos cargados desde API: $puntos")
                        }
                    }
                } catch (e: Exception) {
                    println("Error cargando puntos: ${e.message}")
                } finally {
                    _loadingUsuario.value = false
                }
            }
        }
    }
}