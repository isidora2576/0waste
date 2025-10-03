package com.evaluacion.a0waste_G5_final.ui.theme.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CentersScreen(navController: NavController? = null) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Centros de Reciclaje ♻️") })
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
                "Encuentra Centros de Reciclaje",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                "Próximamente: Mapa con centros de reciclaje cercanos a tu ubicación",
                style = MaterialTheme.typography.bodyMedium
            )

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Centro Norte", style = MaterialTheme.typography.bodyLarge)
                    Text("Av. Principal 123", style = MaterialTheme.typography.bodyMedium)
                    Text("Acepta: plástico, vidrio, papel", style = MaterialTheme.typography.bodySmall)
                }
            }

            Card(modifier = Modifier.fillMaxWidth()) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Centro Sur", style = MaterialTheme.typography.bodyLarge)
                    Text("Calle Secundaria 456", style = MaterialTheme.typography.bodyMedium)
                    Text("Acepta: metal, electrónicos", style = MaterialTheme.typography.bodySmall)
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