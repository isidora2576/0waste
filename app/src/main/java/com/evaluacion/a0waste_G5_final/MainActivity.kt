package com.evaluacion.a0waste_G5_final

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.evaluacion.a0waste_G5_final.Data.SessionManager
import com.evaluacion.a0waste_G5_final.Navigation.Screen
import com.evaluacion.a0waste_G5_final.Viewmodel.UsuarioViewModel
import com.evaluacion.a0waste_G5_final.Viewmodel.WasteViewModel
import com.evaluacion.a0waste_G5_final.ui.theme.Screens.*
import com.evaluacion.a0waste_G5_final.ui.theme.Theme._0waste_G5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _0waste_G5Theme {
                // Inicializar ViewModels
                val wasteViewModel: WasteViewModel = viewModel()
                val usuarioViewModel: UsuarioViewModel = viewModel()
                val navController = rememberNavController()
                val sessionManager = remember { SessionManager(this) }

                // CONFIGURAR Y CARGAR PUNTOS SI HAY USUARIO
                LaunchedEffect(Unit) {
                    // Configurar session manager en UsuarioViewModel
                    usuarioViewModel.setSessionManager(sessionManager)

                    // Verificar si hay usuario logueado y cargar sus puntos
                    if (sessionManager.isLoggedIn()) {
                        println("MainActivity - Usuario logueado, cargando puntos desde API")
                        // WasteViewModel ahora carga puntos sin necesidad de pasar userId
                        wasteViewModel.cargarPuntosDesdeApi()
                    } else {
                        println("MainActivity - No hay usuario logueado")
                    }
                }

                // OBSERVAR CAMBIOS DE NAVEGACIÓN
                ObservarNavegacion(navController, wasteViewModel, sessionManager)

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Login.route,
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        // LOGIN SCREEN
                        composable(route = Screen.Login.route) {
                            LoginScreen(
                                navController = navController,
                                viewModel = usuarioViewModel,
                                sessionManager = sessionManager
                            )
                        }

                        // HOME SCREEN
                        composable(Screen.Home.route) {
                            HomeScreenCompacta(
                                navController = navController,
                                wasteViewModel = wasteViewModel,
                                sessionManager = sessionManager
                            )
                        }

                        // SCAN SCREEN - SOLO API
                        composable(route = Screen.Scan.route) {
                            ScanScreen(
                                navController = navController,
                                viewModel = wasteViewModel,
                                usuarioViewModel = usuarioViewModel,
                                sessionManager = sessionManager
                            )
                        }

                        // CENTERS SCREEN
                        composable(route = Screen.Centers.route) {
                            CentersScreen(navController = navController)
                        }

                        // REWARDS SCREEN - SOLO API
                        composable(route = Screen.Rewards.route) {
                            RewardsScreen(
                                navController = navController,
                                viewModel = wasteViewModel,
                                usuarioViewModel = usuarioViewModel,
                                sessionManager = sessionManager
                            )
                        }

                        // PROFILE SCREEN
                        composable(Screen.Profile.route) {
                            ProfileScreen(
                                navController = navController,
                                viewModel = wasteViewModel,
                                usuarioViewModel = usuarioViewModel,
                                sessionManager = sessionManager
                            )
                        }

                        // REGISTRO SCREEN
                        composable(route = Screen.Registro.route) {
                            RegistroScreen(
                                navController = navController,
                                viewModel = usuarioViewModel,
                                sessionManager = sessionManager
                            )
                        }

                        // RESUMEN SCREEN CON ARGUMENTOS
                        composable(
                            route = "resumen_page?usuario={usuario}",
                            arguments = listOf(
                                navArgument("usuario") {
                                    type = NavType.StringType
                                    nullable = true
                                    defaultValue = ""
                                }
                            )
                        ) { backStackEntry ->
                            val usuarioJson = backStackEntry.arguments?.getString("usuario")
                            ResumenScreen(
                                navController = navController,
                                usuarioJson = usuarioJson
                            )
                        }

                        // STATE SCREEN (Modo Eco)
                        composable(route = "state_page") {
                            StateScreen(navController = navController)
                        }

                        // SUCCESS SCREEN
                        composable(route = "success_page") {
                            SuccessScreen(
                                navController = navController,
                                pointsEarned = 5
                            )
                        }

                    }
                }
            }
        }
    }
}

// FUNCIÓN PARA OBSERVAR CAMBIOS DE NAVEGACIÓN Y RECARGAR PUNTOS
@Composable
fun ObservarNavegacion(
    navController: androidx.navigation.NavHostController,
    wasteViewModel: WasteViewModel,
    sessionManager: SessionManager
) {
    LaunchedEffect(navController) {
        navController.currentBackStackEntryFlow.collect { backStackEntry ->
            backStackEntry?.destination?.route?.let { route ->
                println("📍 Navegación detectada: $route")

                // Recargar puntos cuando navegas a estas pantallas
                val pantallasParaRecargar = listOf(
                    Screen.Home.route,
                    Screen.Profile.route,
                    Screen.Rewards.route,
                    "success_page"
                )

                if (pantallasParaRecargar.contains(route) && sessionManager.isLoggedIn()) {
                    println("   - 🔄 Recargando puntos desde API para: $route")
                    wasteViewModel.cargarPuntosDesdeApi()
                }
            }
        }
    }
}

// PREVIEWS
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    androidx.compose.material3.Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _0waste_G5Theme {
        Greeting("Android")
    }
}