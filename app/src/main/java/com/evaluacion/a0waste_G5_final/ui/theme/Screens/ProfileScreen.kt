package com.evaluacion.a0waste_G5_final.ui.theme.Screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.evaluacion.a0waste_G5_final.Viewmodel.WasteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    navController: NavController? = null,
    viewModel: WasteViewModel
)
{
    val puntos by viewModel.puntosUsuario.collectAsState()
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
            // Estadísticas de impacto
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Mi Impacto Ambiental",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF4CAF50)
                    )
                    Text("• ${(puntos ?: 0) / 5} materiales reciclados")
                    Text("• ${(puntos ?: 0) * 0.2} kg de CO₂ ahorrados")
                    Text("• Nivel: ${when (puntos ?: 0) {
                        in 0..100 -> "Reciclador Principiante"
                        in 101..500 -> "Reciclador Intermedio"
                        else -> "Reciclador Experto"
                    }}")
                    Text("• ${puntos ?: 0} puntos acumulados", color = Color(0xFF4CAF50), fontWeight = FontWeight.Bold)
                }
            }

            // Materiales más reciclados
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        "Materiales Frecuentes",
                        style = MaterialTheme.typography.headlineSmall,
                        color = Color(0xFF4CAF50)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Plástico PET - ${viewModel.getPoints() / 5} veces")
                    Text("• Vidrio - ${viewModel.getPoints() / 7} veces")
                    Text("• Cartón - ${viewModel.getPoints() / 6} veces")
                }
            }

            Button(
                onClick = { navController?.navigate("home_page") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Continuar Reciclando")
            }
        }
    }
}

@SuppressLint("ViewModelConstructorInComposable")
@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    com.evaluacion.a0waste_G5_final.Viewmodel.PreviewWasteViewModel().let { viewModel ->
        ProfileScreen(viewModel = viewModel)
    }
}