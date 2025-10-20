package com.evaluacion.a0waste_G5_final.ui.theme.Screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CentersScreen(navController: NavController? = null) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Centros de Acopio",
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
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Entrega tus materiales aquí",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            Text(
                "Encuentra centros de acopio cerca de ti para entregar materiales voluminosos",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )

            // Centros de acopio realistas
            val centros = listOf(
                Triple("Punto Limpio Municipal", "Av. Principal 123", "Plástico, Vidrio, Cartón"),
                Triple("EcoCenter Mall", "Centro Comercial Norte", "Electrónicos, Pilas"),
                Triple("ReciclaPlus", "Calle Verde 456", "Tetrapak, Aluminio")
            )

            centros.forEach { (nombre, direccion, materiales) ->
                Card(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(nombre, style = MaterialTheme.typography.bodyLarge)
                        Text(direccion, style = MaterialTheme.typography.bodyMedium)
                        Text("Acepta: $materiales",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFF4CAF50))
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

@Preview(showBackground = true)
@Composable
fun CentersScreenPreview() {
    CentersScreen()
}