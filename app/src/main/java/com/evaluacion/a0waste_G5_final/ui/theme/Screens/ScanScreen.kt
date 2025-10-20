package com.evaluacion.a0waste_G5_final.ui.theme.Screens

//pantalla temporal

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.evaluacion.a0waste_G5_final.Viewmodel.WasteViewModel
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ScanScreen(
    navController: NavController? = null,
    viewModel: WasteViewModel
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Escanear Materiales",
                        color = Color.White,
                        fontWeight = FontWeight.Bold
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF81C784)
                )
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
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                "Toma foto de tus materiales reciclables",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                textAlign = TextAlign.Center
            )

            // Simulación de vista de cámara
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .background(Color.Black.copy(alpha = 0.3F))
                    .border(2.dp, Color.White, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Icon(
                        painter = painterResource(id = androidx.core.R.drawable.ic_call_answer),
                        contentDescription = "Cámara",
                        tint = Color.White,
                        modifier = Modifier.size(60.dp)
                    )
                    Text(
                        "Vista previa de cámara",
                        color = Color.White,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

            Text(
                "Consejos para una buena foto:\n• Buena iluminación\n• Enfoca bien el material\n• Muestra todo el material reciclable",
                style = MaterialTheme.typography.bodyMedium,
                color = Color.White.copy(alpha = 0.8f),
                textAlign = TextAlign.Center
            )

            // Botones de acción
            Button(
                onClick = {
                    // Simular éxito de escaneo
                    viewModel.addPoints(25)
                    // Navegar a confirmación
                    navController?.navigate("rewards_page")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF4CAF50)
                )
            ) {
                Text("Toma la Foto ", fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = { navController?.navigate("home_page") },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Volver al Inicio", color = Color.White)
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