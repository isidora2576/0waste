package com.evaluacion.a0waste_G5_final.ui.theme


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController? = null) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("0Waste - Reciclaje Inteligente") })
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "¡Bienvenido a 0Waste!",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Recicla, gana puntos y ayuda al planeta",
                style = MaterialTheme.typography.bodyMedium
            )

            // Botón para escanear residuos
            Button(
                onClick = {
                    // Navegar a pantalla de escaneo
                    navController?.navigate("scan")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("🔍 Escanear Residuos")
            }

            // Botón para centros de reciclaje
            Button(
                onClick = {
                    navController?.navigate("centers")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("🗺️ Centros de Reciclaje")
            }

            // Botón para recompensas
            Button(
                onClick = {
                    navController?.navigate("rewards")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("🎁 Mis Recompensas")
            }

            // Imagen placeholder - luego agregarás tu logo en /res/drawable
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery), // Cambiar por R.drawable.logo
                contentDescription = "Logo 0Waste",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp),
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}