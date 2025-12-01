package com.evaluacion.a0waste_G5_final.ui.theme.Screens

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.evaluacion.a0waste_G5_final.Data.SessionManager
import com.evaluacion.a0waste_G5_final.Model.UsuarioRequest
import com.evaluacion.a0waste_G5_final.Viewmodel.UsuarioViewModel
import com.google.gson.Gson
import java.net.URLEncoder

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController,
    viewModel: UsuarioViewModel = viewModel(),
    sessionManager: SessionManager
) {
    var nombre by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var direccion by remember { mutableStateOf("") }
    var telefono by remember { mutableStateOf("") }
    var tipoReciclador by remember { mutableStateOf("") }
    var materialesInteres by remember { mutableStateOf(emptyList<String>()) }
    var aceptaTerminos by remember { mutableStateOf(false) }
    var aceptaCamara by remember { mutableStateOf(false) }

    val registroState by viewModel.registroState.collectAsState()
    val erroresRegistro by viewModel.erroresRegistro.collectAsState()

    // LAUNCHER para permiso REAL de cámara
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            println("Permiso de cámara OTORGADO por sistema")
            sessionManager.saveAceptoCamara(true)
        } else {
            println("Permiso de cámara DENEGADO por sistema")
            aceptaCamara = false
            sessionManager.saveAceptoCamara(false)
        }
    }

    LaunchedEffect(registroState) {
        registroState?.onSuccess { response ->
            val usuarioDatos = mapOf(
                "nombre" to nombre,
                "email" to email,
                "password" to password,
                "direccion" to direccion,
                "telefono" to telefono,
                "tipoReciclador" to tipoReciclador,
                "materialesInteres" to materialesInteres,
                "aceptaTerminos" to aceptaTerminos,
                "permisoCamara" to aceptaCamara
            )
            val usuarioJson = Gson().toJson(usuarioDatos)
            navController.navigate("resumen_page?usuario=${URLEncoder.encode(usuarioJson, "UTF-8")}")
        }?.onFailure { error ->
            println("Error en registro: ${error.message}")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text("Unirse a 0Waste", color = Color.White, fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF81C784)),
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, "Volver", tint = Color.White)
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFF4CAF50))
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            Text(
                "Comienza tu viaje reciclaje",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                "Registrate para ganar puntos por cada material que recicles",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f),
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            // CAMPO NOMBRE
            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    viewModel.limpiarErroresRegistro()
                },
                label = { Text("Nombre completo", color = Color.White) },
                isError = erroresRegistro["nombre"] != null,
                supportingText = {
                    erroresRegistro["nombre"]?.let { error ->
                        Text(text = error, color = Color(0xFFFF6B6B))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f),
                    errorIndicatorColor = Color(0xFFFF6B6B)
                )
            )

            // CAMPO EMAIL
            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    viewModel.limpiarErroresRegistro()
                },
                label = { Text("Correo electrónico", color = Color.White) },
                isError = erroresRegistro["email"] != null,
                supportingText = {
                    erroresRegistro["email"]?.let { error ->
                        Text(text = error, color = Color(0xFFFF6B6B))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f),
                    errorIndicatorColor = Color(0xFFFF6B6B)
                )
            )

            // CAMPO CONTRASEÑA
            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                    viewModel.limpiarErroresRegistro()
                },
                label = { Text("Contraseña", color = Color.White) },
                visualTransformation = PasswordVisualTransformation(),
                isError = erroresRegistro["password"] != null,
                supportingText = {
                    erroresRegistro["password"]?.let { error ->
                        Text(text = error, color = Color(0xFFFF6B6B))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f),
                    errorIndicatorColor = Color(0xFFFF6B6B)
                )
            )

            // CAMPO DIRECCIÓN
            OutlinedTextField(
                value = direccion,
                onValueChange = {
                    direccion = it
                    viewModel.limpiarErroresRegistro()
                },
                label = { Text("Dirección", color = Color.White) },
                isError = erroresRegistro["direccion"] != null,
                supportingText = {
                    erroresRegistro["direccion"]?.let { error ->
                        Text(text = error, color = Color(0xFFFF6B6B))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f),
                    errorIndicatorColor = Color(0xFFFF6B6B)
                )
            )

            // CAMPO TELÉFONO
            OutlinedTextField(
                value = telefono,
                onValueChange = {
                    telefono = it
                    viewModel.limpiarErroresRegistro()
                },
                label = { Text("Teléfono", color = Color.White) },
                isError = erroresRegistro["telefono"] != null,
                supportingText = {
                    erroresRegistro["telefono"]?.let { error ->
                        Text(text = error, color = Color(0xFFFF6B6B))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                colors = TextFieldDefaults.colors(
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedLabelColor = Color.White,
                    unfocusedLabelColor = Color.White.copy(alpha = 0.7f),
                    cursorColor = Color.White,
                    focusedIndicatorColor = Color.White,
                    unfocusedIndicatorColor = Color.White.copy(alpha = 0.5f),
                    errorIndicatorColor = Color(0xFFFF6B6B)
                )
            )

            // TIPO DE RECICLADOR
            Text(
                "¿Cuál es tu experiencia reciclando?",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                listOf("Principiante", "Intermedio", "Avanzado").forEach { tipo ->
                    FilterChip(
                        selected = tipoReciclador == tipo,
                        onClick = {
                            tipoReciclador = tipo
                            viewModel.limpiarErroresRegistro()
                        },
                        label = { Text(tipo, color = if (tipoReciclador == tipo) Color(0xFF4CAF50) else Color.White) },
                        modifier = Modifier.weight(1f),
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = Color.White,
                            selectedLabelColor = Color(0xFF4CAF50)
                        )
                    )
                }
            }

            // ERROR TIPO RECICLADOR
            erroresRegistro["tipoReciclador"]?.let { error ->
                Text(
                    text = error,
                    color = Color(0xFFFF6B6B),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            // MATERIALES DE INTERÉS
            Text(
                "¿Qué materiales sueles reciclar?",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            )

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                val materiales = listOf("Plástico PET", "Vidrio", "Aluminio", "Cartón", "Papel", "Tetrapak", "Electrónicos", "Pilas")

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    materiales.take(4).forEach { material ->
                        AssistChip(
                            onClick = {
                                materialesInteres = if (materialesInteres.contains(material)) {
                                    materialesInteres - material
                                } else {
                                    materialesInteres + material
                                }
                            },
                            label = { Text(material) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = if (materialesInteres.contains(material))
                                    Color.White else Color.White.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    materiales.drop(4).forEach { material ->
                        AssistChip(
                            onClick = {
                                materialesInteres = if (materialesInteres.contains(material)) {
                                    materialesInteres - material
                                } else {
                                    materialesInteres + material
                                }
                            },
                            label = { Text(material) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = if (materialesInteres.contains(material))
                                    Color.White else Color.White.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            // PERMISO DE CÁMARA (SOLICITUD REAL)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = aceptaCamara,
                    onCheckedChange = { quierePermiso ->
                        aceptaCamara = quierePermiso

                        if (quierePermiso) {
                            // PEDIR PERMISO REAL DEL SISTEMA
                            cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
                        } else {
                            // Si desmarca, guardar que NO aceptó
                            sessionManager.saveAceptoCamara(false)
                        }
                    },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.White,
                        uncheckedColor = Color.White.copy(alpha = 0.7f),
                        checkmarkColor = Color(0xFF4CAF50)
                    )
                )
                Spacer(Modifier.width(8.dp))
                Column {
                    Text(
                        "Permitir acceso a la cámara",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Necesario para tomar fotos de evidencia de reciclaje",
                        color = Color.White.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            // TÉRMINOS Y CONDICIONES
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = aceptaTerminos,
                    onCheckedChange = { aceptaTerminos = it },
                    colors = CheckboxDefaults.colors(
                        checkedColor = Color.White,
                        uncheckedColor = Color.White.copy(alpha = 0.7f),
                        checkmarkColor = Color(0xFF4CAF50)
                    )
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "Acepto los términos y condiciones de 0Waste",
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // ERROR TÉRMINOS
            erroresRegistro["terminos"]?.let { error ->
                Text(
                    text = error,
                    color = Color(0xFFFF6B6B),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // BOTÓN DE REGISTRO
            Button(
                onClick = {
                    if (viewModel.validarRegistroLocal(
                            nombre, email, password, direccion, telefono,
                            tipoReciclador, aceptaTerminos, aceptaCamara
                        )) {
                        val usuarioRequest = UsuarioRequest(
                            nombreCompleto = nombre,
                            email = email,
                            password = password,
                            direccion = direccion,
                            telefono = telefono,
                            tipoReciclador = tipoReciclador,
                            materialesInteres = materialesInteres,
                            aceptaTerminos = aceptaTerminos,
                            permisoCamara = aceptaCamara
                        )
                        viewModel.registrarUsuario(usuarioRequest)
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF4CAF50)
                ),
                enabled = aceptaTerminos
            ) {
                Text("Comenzar a reciclar", fontWeight = FontWeight.Bold)
            }

            TextButton(
                onClick = { navController.navigate("login_page") }
            ) {
                Text(
                    "¿Ya tienes cuenta? Inicia sesión aquí",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium
                )
            }

            Spacer(modifier = Modifier.height(32.dp))
        }
    }
}