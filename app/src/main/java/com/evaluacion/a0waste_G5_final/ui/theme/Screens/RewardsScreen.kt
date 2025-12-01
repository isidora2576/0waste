package com.evaluacion.a0waste_G5_final.ui.theme.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardsScreen(
    navController: NavController? = null,
    viewModel: WasteViewModel,
    usuarioViewModel: UsuarioViewModel = viewModel(),
    sessionManager: SessionManager = SessionManager(LocalContext.current)
) {
    val puntosState by usuarioViewModel.puntosState.collectAsState()
    val usuarioId = remember { sessionManager.getUserId() }


    LaunchedEffect(Unit) {
        usuarioViewModel.setSessionManager(sessionManager)
    }

    // Observar resultado de canje en API
    LaunchedEffect(puntosState) {
        puntosState?.onSuccess { response ->
            println("Canje exitoso en API")
        }?.onFailure { error ->
            println("Error en canje API: ${error.message}")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Mis Puntos y Recompensas",
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
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // Tarjeta de puntos
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Column(
                    modifier = Modifier.padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Tus Puntos Acumulados",
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF4CAF50))

                    // Intentar obtener puntos de API primero
                    val puntosApi = usuarioViewModel.puntosUsuario.collectAsState().value
                    val puntosLocales = viewModel.getPoints()
                    val puntosMostrar = if (puntosApi > 0) puntosApi else puntosLocales

                    Text(
                        "$puntosMostrar puntos",
                        style = MaterialTheme.typography.displaySmall,
                        color = Color(0xFF4CAF50),
                        fontWeight = FontWeight.Bold
                    )

                    // Mostrar fuente
                    val fuente = if (usuarioId > 0L && puntosApi > 0) "Sincronizados con nube"
                    else "Almacenamiento local"
                    Text(
                        fuente,
                        style = MaterialTheme.typography.bodySmall,
                        color = if (usuarioId > 0L) Color(0xFF4CAF50) else Color.Gray
                    )

                    Text(
                        "Cada foto vale 5 puntos",
                        style = MaterialTheme.typography.bodySmall,
                        color = Color.Gray
                    )
                }
            }

            // Recompensas disponibles
            Text(
                "Recompensas Disponibles",
                style = MaterialTheme.typography.headlineSmall,
                color = Color.White,
                modifier = Modifier.fillMaxWidth()
            )

            // Lista de recompensas
            val recompensas = listOf(
                "50 pts - 10% descuento tienda ecológica" to 50,
                "100 pts - Bolsa reutilizable" to 100,
                "150 pts - Taza sustentable" to 150,
                "200 pts - Kit reciclaje hogar" to 200,
                "300 pts - Compostera pequeña" to 300,
                "500 pts - Suscripción 3 meses premium" to 500
            )

            LazyColumn(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(recompensas) { (recompensa, puntosRequeridos) ->
                    Card(
                        modifier = Modifier.fillMaxWidth(),
                        colors = CardDefaults.cardColors(containerColor = Color.White)
                    ) {
                        Row(
                            modifier = Modifier.padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.weight(1f)
                            ) {
                                Text(
                                    recompensa,
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Medium
                                )
                                Text(
                                    "$puntosRequeridos puntos",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF4CAF50)
                                )
                            }

                            // Botón de canje
                            OutlinedButton(
                                onClick = {
                                    if (viewModel.getPoints() >= puntosRequeridos) {

                                        val usuarioId = sessionManager.getUserId()

                                        if (usuarioId > 0L) {
                                            println("Canjeando recompensa para usuario ID: $usuarioId")


                                            //CANJEAR EN API
                                            usuarioViewModel.canjearPuntosApi(
                                                usuarioId,
                                                puntosRequeridos,
                                                recompensa
                                            )
                                        } else {
                                            println("No hay usuario logueado para canjear")
                                        }
                                    }
                                },
                                enabled = viewModel.getPoints() >= puntosRequeridos,
                                modifier = Modifier
                                    .height(34.dp)
                                    .width(100.dp),
                                colors = ButtonDefaults.outlinedButtonColors(
                                    contentColor = Color(0xFF4CAF50)
                                )
                            ) {
                                Text(
                                    "Cambiar",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            Button(
                onClick = { navController?.navigate("home_page") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al Inicio")
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun RewardsScreenPreview() {
    com.evaluacion.a0waste_G5_final.Viewmodel.PreviewWasteViewModel().apply {
    }.let { viewModel ->
        RewardsScreen(viewModel = viewModel)
    }
}