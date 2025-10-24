package com.evaluacion.a0waste_G5_final.ui.theme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
) {
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
                    Spacer(modifier = Modifier.height(12.dp))
                    Text("• ${viewModel.getPoints() / 25} materiales reciclados")
                    Text("• ${viewModel.getPoints() * 0.1} kg de CO₂ ahorrados")
                    Text("• Nivel: ${when (viewModel.getPoints()) {
                        in 0..100 -> "Reciclador Principiante"
                        in 101..500 -> "Reciclador Intermedio"
                        else -> "Reciclador Experto"
                    }}")
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
                    Text("• Plástico PET - ${viewModel.getPoints() / 10} veces")
                    Text("• Vidrio - ${viewModel.getPoints() / 15} veces")
                    Text("• Cartón - ${viewModel.getPoints() / 12} veces")
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


@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {

    val previewViewModel = object : WasteViewModel() {
        override fun getPoints(): Int = 150
        override fun addPoints(points: Int) {}
    }

    ProfileScreen(viewModel = previewViewModel)
}