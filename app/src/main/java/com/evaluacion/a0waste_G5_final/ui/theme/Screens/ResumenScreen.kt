package com.evaluacion.a0waste_G5_final.ui.theme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResumenScreen(
    navController: NavController,
    usuarioJson: String? = null
) {
    //PARSEAR DATOS DEL USUARIO
    val usuarioData = remember(usuarioJson) {
        if (usuarioJson.isNullOrEmpty()) {
            emptyMap<String, Any>()
        } else {
            try {
                val type = object : TypeToken<Map<String, Any>>() {}.type
                Gson().fromJson<Map<String, Any>>(usuarioJson, type) ?: emptyMap()
            } catch (e: Exception) {
                println("Error parseando JSON: ${e.message}")
                emptyMap()
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Resumen de Registro",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF81C784)
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFF4CAF50))
                .verticalScroll(rememberScrollState())
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.CheckCircle,
                contentDescription = "Registro exitoso",
                tint = Color.White,
                modifier = Modifier.size(80.dp)
            )

            Text(
                "¡Bienvenido a 0Waste!",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Text(
                "Tu registro se ha completado exitosamente",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Text(
                        "Tus datos registrados:",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.Bold
                    )

                    // MOSTRAR DATOS REALES DEL REGISTRO
                    if (usuarioData.isNotEmpty()) {
                        Text("Nombre: ${usuarioData["nombre"] ?: "No disponible"}")
                        Text("Correo: ${usuarioData["email"] ?: "No disponible"}")
                        Text("Dirección: ${usuarioData["direccion"] ?: "No disponible"}")
                        Text("Teléfono: ${usuarioData["telefono"] ?: "No disponible"}")
                        Text("Tipo de reciclador: ${usuarioData["tipoReciclador"] ?: "No disponible"}")

                        val materiales = usuarioData["materialesInteres"] as? List<*>
                        if (materiales != null && materiales.isNotEmpty()) {
                            Text("Materiales de interés: ${materiales.joinToString(", ")}")
                        }

                        val aceptaTerminos = usuarioData["aceptaTerminos"] as? Boolean
                        Text("Términos: ${if (aceptaTerminos == true) "Aceptados" else "No aceptados"}")

                        val permisoCamara = usuarioData["permisoCamara"] as? Boolean
                        Text("Permiso cámara: ${if (permisoCamara == true) "Concedido" else "No concedido"}")
                    } else {
                        // Datos de ejemplo si no hay datos reales
                        Text("Nombre: Usuario Registrado")
                        Text("Correo: usuario@0waste.com")
                        Text("Dirección: Dirección del usuario")
                        Text("Teléfono: 123456789")
                        Text("Tipo de reciclador: Principiante")
                        Text("Términos: Aceptados")
                        Text("Permiso cámara: Concedido")
                    }
                }
            }

            Text(
                "Comienza a reciclar y gana puntos por ayudar al planeta",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )

            Button(
                onClick = {
                    navController.navigate("home_page") {
                        popUpTo("registro_page") { inclusive = true }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = androidx.compose.material3.ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF4CAF50)
                )
            ) {
                Text(
                    "Comenzar en 0Waste",
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}