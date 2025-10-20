package com.evaluacion.a0waste_G5_final.Viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class LoginViewModel : ViewModel() {

    private val _loginState = MutableStateFlow(LoginUiState())
    val loginState: StateFlow<LoginUiState> = _loginState.asStateFlow()

    fun onEmailChange(email: String) {
        _loginState.value = _loginState.value.copy(
            email = email,
            errors = _loginState.value.errors.copy(email = null) // ✅ CORREGIDO
        )
    }

    fun onPasswordChange(password: String) {
        _loginState.value = _loginState.value.copy(
            password = password,
            errors = _loginState.value.errors.copy(password = null) // ✅ CORREGIDO
        )
    }

    fun onRememberMeChange(rememberMe: Boolean) {
        _loginState.value = _loginState.value.copy(rememberMe = rememberMe)
    }

    fun validateLogin(): Boolean {
        val currentState = _loginState.value
        val errors = LoginErrors(
            email = if (currentState.email.isBlank()) "Email es requerido"
            else if (!currentState.email.contains("@")) "Email inválido"
            else null,
            password = if (currentState.password.isBlank()) "Contraseña es requerida"
            else if (currentState.password.length < 6) "Mínimo 6 caracteres"
            else null
        )

        val hasErrors = listOfNotNull(errors.email, errors.password).isNotEmpty()

        _loginState.value = currentState.copy(errors = errors)
        return !hasErrors
    }

    // Simulación de login (en una app real harías una llamada a API)
    fun loginUser(): Boolean {
        return validateLogin() && _loginState.value.email == "usuario@0waste.com" && _loginState.value.password == "123456"
    }
}

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val rememberMe: Boolean = false,
    val errors: LoginErrors = LoginErrors()
)

data class LoginErrors(
    val email: String? = null,
    val password: String? = null
)