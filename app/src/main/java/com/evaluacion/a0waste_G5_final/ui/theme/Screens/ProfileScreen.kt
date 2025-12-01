package com.evaluacion.a0waste_G5_final.ui.theme.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.evaluacion.a0waste_G5_final.Data.SessionManager
import com.evaluacion.a0waste_G5_final.Viewmodel.UsuarioViewModel
import com.evaluacion.a0waste_G5_final.Viewmodel.WasteViewModel

@SuppressLint("UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController? = null,
    wasteViewModel: WasteViewModel? = null,
    usuarioViewModel: UsuarioViewModel = viewModel(),
    sessionManager: SessionManager = SessionManager(LocalContext.current),
    viewModel: Any
) {
    // Estados de ViewModels
    val puntosLocales by wasteViewModel?.puntosUsuario?.collectAsState() ?: mutableStateOf(0)
    val cargandoApi by wasteViewModel?.cargando?.collectAsState() ?: mutableStateOf(false)

    // Estados de UsuarioViewModel (API)
    val usuarioData by usuarioViewModel.usuarioData.collectAsState()
    val puntosApi by usuarioViewModel.puntosUsuario.collectAsState()
    val loadingUsuario by usuarioViewModel.loadingUsuario.collectAsState()
    val historialPuntos by usuarioViewModel.historialPuntos.collectAsState()

    // SessionManager
    val userId = sessionManager.getUserId()
    val userEmail = sessionManager.getUserEmail()
    val userName = sessionManager.getUserName()
    val isLoggedIn = sessionManager.isLoggedIn()

    // Efecto para cargar datos del usuario cuando la pantalla se muestra
    LaunchedEffect(Unit) {
        usuarioViewModel.setSessionManager(sessionManager)

        if (isLoggedIn && userId > 0L) {
            println("🔍 Cargando datos del usuario ID: $userId desde API")
            usuarioViewModel.obtenerUsuarioDesdeApi(userId)

        } else {
            println("No hay usuario logueado en ProfileScreen")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mi Perfil 0Waste",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color(0xFF81C784))
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .background(Color(0xFF4CAF50))
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Mostrar estado de carga
            if (loadingUsuario || cargandoApi) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        CircularProgressIndicator(color = Color.White)
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            if (isLoggedIn) "Cargando datos desde la API..."
                            else "Cargando perfil local...",
                            color = Color.White
                        )
                    }
                }
            } else {
                // Información del usuario
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Información Personal",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color(0xFF4CAF50),
                            fontWeight = FontWeight.Bold
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        if (usuarioData != null) {
                            // Datos desde API
                            Text("• Nombre: ${usuarioData!!.nombreCompleto}")
                            Text("• Email: ${usuarioData!!.email}")
                            Text("• Dirección: ${usuarioData!!.direccion}")
                            Text("• Teléfono: ${usuarioData!!.telefono}")
                            Text("• Tipo: ${usuarioData!!.tipoReciclador}")
                        } else if (isLoggedIn) {
                            // Datos desde SessionManager
                            Text("• Nombre: $userName")
                            Text("• Email: $userEmail")
                            Text("• ID: $userId")
                            Text("• Estado: Logueado")
                        } else {
                            Text("• Estado: No logueado")
                            Text("• Usa datos locales")
                        }
                    }
                }

                // Estadísticas de impacto (usando datos reales)
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Mi Impacto Ambiental",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color(0xFF4CAF50)
                        )

                        // Usar puntos de API si están disponibles, sino locales
                        val puntosMostrar = if (usuarioData != null) usuarioData!!.puntosActuales else puntosLocales ?: 0

                        Text("• ${(puntosMostrar) / 5} materiales reciclados estimados")
                        Text("• ${(puntosMostrar) * 0.2} kg de CO₂ ahorrados estimados")

                        // Determinar nivel basado en puntos
                        val nivel = when (puntosMostrar) {
                            in 0..100 -> "Reciclador Principiante"
                            in 101..500 -> "Reciclador Intermedio"
                            else -> "Reciclador Experto"
                        }
                        Text("• Nivel: $nivel")

                        // Mostrar fuente de los puntos
                        val fuentePuntos = if (usuarioData != null) "API" else "Almacenamiento local"
                        Text("• $puntosMostrar puntos acumulados ($fuentePuntos)",
                            color = Color(0xFF4CAF50),
                            fontWeight = FontWeight.Bold)
                    }
                }

                // Materiales más reciclados (basados en puntos)
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            "Materiales Frecuentes",
                            style = MaterialTheme.typography.headlineSmall,
                            color = Color(0xFF4CAF50)
                        )
                        Spacer(modifier = Modifier.height(8.dp))

                        val puntosMostrar = if (usuarioData != null) usuarioData!!.puntosActuales else puntosLocales ?: 0

                        Text("• Plástico PET - ${puntosMostrar / 5} veces estimadas")
                        Text("• Vidrio - ${puntosMostrar / 7} veces estimadas")
                        Text("• Cartón - ${puntosMostrar / 6} veces estimadas")

                        if (usuarioData != null && usuarioData!!.materialesInteres.isNotEmpty()) {
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("Materiales de interés:",
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFF4CAF50))
                            usuarioData!!.materialesInteres.forEach { material ->
                                Text("   ✓ $material")
                            }
                        }
                    }
                }

                // Botones de acción
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (!isLoggedIn) {
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            colors = CardDefaults.cardColors(
                                containerColor = Color.Yellow.copy(alpha = 0.2f)
                            )
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    "No estás logueado",
                                    color = Color.Yellow,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    "Tus puntos se guardan localmente. Inicia sesión para sincronizarlos con la nube.",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.Yellow.copy(alpha = 0.8f)
                                )
                            }
                        }

                        Button(
                            onClick = { navController?.navigate("login_page") },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color(0xFF4CAF50)
                            )
                        ) {
                            Text("Iniciar Sesión para Sincronizar")
                        }
                    }

                    Button(
                        onClick = {
                            if (isLoggedIn && userId > 0L) {
                                // Refrescar datos desde API
                                usuarioViewModel.obtenerUsuarioDesdeApi(userId)
                            }
                            navController?.navigate("home_page")
                        },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("Continuar Reciclando")
                    }

                    if (isLoggedIn) {
                        OutlinedButton(
                            onClick = {
                                // Cerrar sesión
                                sessionManager.logout()
                                navController?.navigate("login_page") {
                                    popUpTo("home_page") { inclusive = true }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = Color(0xFFFF6B6B)
                            )
                        ) {
                            Text("Cerrar Sesión")
                        }
                    }
                }
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable", "UnrememberedMutableState")
@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    ProfileScreen(
        viewModel = com.evaluacion.a0waste_G5_final.Viewmodel.PreviewWasteViewModel(),
        usuarioViewModel = androidx.lifecycle.viewmodel.compose.viewModel()
    )
}