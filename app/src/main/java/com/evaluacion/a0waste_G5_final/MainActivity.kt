package com.evaluacion.a0waste_G5_final

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.evaluacion.a0waste_G5_final.Navigation.Screen
import com.evaluacion.a0waste_G5_final.Viewmodel.UsuarioViewModel
import com.evaluacion.a0waste_G5_final.Viewmodel.WasteViewModel
import com.evaluacion.a0waste_G5_final.ui.theme.Screens.CentersScreen
import com.evaluacion.a0waste_G5_final.ui.theme.Screens.HomeScreenCompacta
import com.evaluacion.a0waste_G5_final.ui.theme.Screens.LoginScreen
import com.evaluacion.a0waste_G5_final.ui.theme.Screens.StateScreen
import com.evaluacion.a0waste_G5_final.ui.theme.Screens.ProfileScreen
import com.evaluacion.a0waste_G5_final.ui.theme.Screens.RegistroScreen
import com.evaluacion.a0waste_G5_final.ui.theme.Screens.ResumenScreen
import com.evaluacion.a0waste_G5_final.ui.theme.Screens.RewardsScreen
import com.evaluacion.a0waste_G5_final.ui.theme.Screens.ScanScreen
import com.evaluacion.a0waste_G5_final.ui.theme.Theme._0waste_G5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        enableEdgeToEdge()
        setContent {
            _0waste_G5Theme {
                val viewModel: WasteViewModel = viewModel()
                val usuarioViewModel: UsuarioViewModel = viewModel()
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Login.route,
                        modifier = Modifier.padding(innerPadding),
                    ) {
                        composable(route = Screen.Home.route) {
                            HomeScreenCompacta(navController = navController, viewModel = viewModel)
                        }
                        composable(route = Screen.Scan.route) {
                            ScanScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(route = Screen.Centers.route) {
                            CentersScreen(navController = navController)
                        }
                        composable(route = Screen.Rewards.route) {
                            RewardsScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(route = Screen.Profile.route) {
                            ProfileScreen(navController = navController, viewModel = viewModel)
                        }
                        composable(route = Screen.Registro.route) {
                            RegistroScreen(navController = navController, viewModel = usuarioViewModel)
                        }
                        composable(route = Screen.Resumen.route) {
                            ResumenScreen(navController = navController, viewModel = usuarioViewModel)
                        }

                        composable(route = "state_page") {
                            StateScreen()
                        }
                        composable(route = Screen.Login.route) {
                            LoginScreen(navController = navController)
                        }
                    }
                }
            }
        }
    }
}

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