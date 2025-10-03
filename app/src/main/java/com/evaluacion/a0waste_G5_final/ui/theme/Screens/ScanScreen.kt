package com.evaluacion.a0waste_G5_final.ui.theme.Screens

//pantalla temporal

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.evaluacion.a0waste_G5_final.Viewmodel.WasteViewModel
import androidx.compose.runtime.remember
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(
    navController: NavController? = null,
    viewModel: WasteViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Escanear Residuos") })
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
                "Pantalla de Escaneo",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                "Aquí podrás escanear códigos de barras de productos para identificar materiales reciclables",
                style = MaterialTheme.typography.bodyMedium
            )

            Button(
                onClick = {
                    viewModel.addPoints(50)
                    navController?.navigate("home_page")
                }
            ) {
                Text("Simular Escaneo (+50 puntos)")
            }

            Button(
                onClick = { navController?.navigate("home_page") }
            ) {
                Text("Volver al Inicio")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ScanScreenPreview() {
    ScanScreen(
        navController = null,
        viewModel = remember { WasteViewModel() }
    )
}