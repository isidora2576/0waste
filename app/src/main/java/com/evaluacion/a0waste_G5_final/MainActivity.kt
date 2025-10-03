package com.evaluacion.a0waste_G5_final

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.compose.material3.Text
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.evaluacion.a0waste_G5_final.ui.theme.HomeScreen
import com.evaluacion.a0waste_G5_final.ui.theme._0waste_G5Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _0waste_G5Theme {
                val navController = rememberNavController()

                NavHost(
                    navController = navController,
                    startDestination = "home"
                ) {
                    composable("home") {
                        HomeScreen(navController = navController)
                    }
                    // Pantallas temporales para que funcione la navegación
                    composable("scan") {
                        // Pantalla temporal de escaneo
                        Text("Pantalla de Escaneo - En desarrollo")
                    }
                    composable("centers") {
                        // Pantalla temporal de centros
                        Text("Pantalla de Centros - En desarrollo")
                    }
                    composable("rewards") {
                        // Pantalla temporal de recompensas
                        Text("Pantalla de Recompensas - En desarrollo")
                    }
                }
            }
        }
    }
}
