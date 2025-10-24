package com.evaluacion.a0waste_G5_final.ui.theme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.evaluacion.a0waste_G5_final.Viewmodel.UsuarioViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegistroScreen(
    navController: NavController,
    viewModel: UsuarioViewModel
) {
    val estado by viewModel.estado.collectAsState()

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

            // Campo nombre
            OutlinedTextField(
                value = estado.nombre,
                onValueChange = viewModel::onNombreChange,
                label = { Text("Nombre completo") },
                isError = estado.errores.nombre != null,
                supportingText = {
                    estado.errores.nombre?.let {
                        Text(text = it, color = Color(0xFFFF6B6B))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Campo correo
            OutlinedTextField(
                value = estado.correo,
                onValueChange = viewModel::onCorreoChange,
                label = { Text("Correo electrónico") },
                isError = estado.errores.correo != null,
                supportingText = {
                    estado.errores.correo?.let {
                        Text(text = it, color = Color(0xFFFF6B6B))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Campo clave
            OutlinedTextField(
                value = estado.clave,
                onValueChange = viewModel::onClaveChange,
                label = { Text("Contraseña") },
                visualTransformation = PasswordVisualTransformation(),
                isError = estado.errores.clave != null,
                supportingText = {
                    estado.errores.clave?.let {
                        Text(text = it, color = Color(0xFFFF6B6B))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Campo direccion
            OutlinedTextField(
                value = estado.direccion,
                onValueChange = viewModel::onDireccionChange,
                label = { Text("Dirección") },
                isError = estado.errores.direccion != null,
                supportingText = {
                    estado.errores.direccion?.let {
                        Text(text = it, color = Color(0xFFFF6B6B))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            // Campo teléfono
            OutlinedTextField(
                value = estado.telefono,
                onValueChange = viewModel::onTelefonoChange,
                label = { Text("Teléfono") },
                isError = estado.errores.telefono != null,
                supportingText = {
                    estado.errores.telefono?.let {
                        Text(text = it, color = Color(0xFFFF6B6B))
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
            )

            // Selector de tipo de reciclador
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
                viewModel.tiposReciclador.forEach { tipo ->
                    FilterChip(
                        selected = estado.tipoReciclador == tipo,
                        onClick = { viewModel.onTipoRecicladorChange(tipo) },
                        label = { Text(tipo) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            if (estado.errores.tipoReciclador != null) {
                Text(
                    text = estado.errores.tipoReciclador!!,
                    color = Color(0xFFFF6B6B),
                    style = MaterialTheme.typography.bodySmall
                )
            }


            Text(
                "¿Qué materiales sueles reciclar?",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            )


            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // Primera fila de materiales
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    viewModel.materialesDisponibles.take(4).forEach { material ->
                        AssistChip(
                            onClick = {
                                viewModel.onMaterialInteresChange(
                                    material,
                                    !estado.materialesInteres.contains(material)
                                )
                            },
                            label = { Text(material) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = if (estado.materialesInteres.contains(material))
                                    Color.White else Color.White.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Segunda fila de materiales
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    viewModel.materialesDisponibles.drop(4).forEach { material ->
                        AssistChip(
                            onClick = {
                                viewModel.onMaterialInteresChange(
                                    material,
                                    !estado.materialesInteres.contains(material)
                                )
                            },
                            label = { Text(material) },
                            colors = AssistChipDefaults.assistChipColors(
                                containerColor = if (estado.materialesInteres.contains(material))
                                    Color.White else Color.White.copy(alpha = 0.2f)
                            ),
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }

            //Permisos de cámara
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = estado.aceptaCamara,
                    onCheckedChange = viewModel::onAceptaCamaraChange
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

            if (estado.errores.aceptaCamara != null) {
                Text(
                    text = estado.errores.aceptaCamara!!,
                    color = Color(0xFFFF6B6B),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            // Checkbox: términos y condiciones
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = estado.aceptaTerminos,
                    onCheckedChange = viewModel::onAceptarTerminosChange
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    "Acepto los términos y condiciones de 0Waste",
                    color = Color.White,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón de registro
            Button(
                onClick = {
                    if (viewModel.validarFormulario() && estado.aceptaTerminos) {
                        navController.navigate("resumen_page")
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF4CAF50)
                ),
                enabled = estado.aceptaTerminos
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