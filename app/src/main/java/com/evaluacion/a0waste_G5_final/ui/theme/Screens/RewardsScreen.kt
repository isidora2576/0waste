package com.evaluacion.a0waste_G5_final.ui.theme.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.evaluacion.a0waste_G5_final.Viewmodel.WasteViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RewardsScreen(navController: NavController? = null, viewModel: WasteViewModel) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Mis Recompensas") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                "Tus Recompensas",
                style = MaterialTheme.typography.headlineMedium
            )

            // Tarjeta de puntos
            Card(
                modifier = Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text("Puntos Acumulados", style = MaterialTheme.typography.bodyMedium)
                    Text(
                        "${viewModel.getPoints()} puntos",
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // Cupones disponibles
            Text(
                "Cupones Disponibles",
                style = MaterialTheme.typography.headlineSmall
            )

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Café Gratis", style = MaterialTheme.typography.bodyLarge)
                    Text("100 puntos - Cafetería Verde", style = MaterialTheme.typography.bodyMedium)
                    Text("Canjea en cualquier sucursal", style = MaterialTheme.typography.bodySmall)
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("20% Descuento", style = MaterialTheme.typography.bodyLarge)
                    Text("200 puntos - Tienda Eco", style = MaterialTheme.typography.bodyMedium)
                    Text("En productos seleccionados", style = MaterialTheme.typography.bodySmall)
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
fun RewardsScreenPreview() {
    RewardsScreen(viewModel = WasteViewModel())
}