package com.evaluacion.a0waste_G5_final.ui.theme.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.evaluacion.a0waste_G5_final.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenExpandida(navController: NavController? = null) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "0Waste - Plataforma de Reciclaje Inteligente",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xFF81C784)
                )
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFF4CAF50)),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(60.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(32.dp, Alignment.CenterVertically)
            ) {
                // Logo muy grande
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo 0Waste",
                    modifier = Modifier.size(320.dp),
                    contentScale = ContentScale.Fit
                )

                // Títulos centrados
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Transforma tu reciclaje",
                        color = Color.White,
                        style = MaterialTheme.typography.displayLarge,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )

                    Text(
                        text = "en recompensas reales",
                        color = Color.White,
                        style = MaterialTheme.typography.displayMedium,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    Text(
                        text = "Toma fotos de evidencias de reciclaje y acumula puntos para canjear por descuentos en establecimientos asociados",
                        color = Color.White.copy(alpha = 0.9f),
                        style = MaterialTheme.typography.headlineMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 24.dp)
                    )
                }

                // Botones más anchos pero proporcionales
                Row(
                    modifier = Modifier.fillMaxWidth(0.7f),
                    horizontalArrangement = Arrangement.spacedBy(24.dp)
                ) {
                    Button(
                        onClick = { navController?.navigate("scan_page") },
                        modifier = Modifier
                            .weight(1f)
                            .height(80.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF4CAF50)
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                    ) {
                        Text(
                            "Comenzar a Escanear",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    OutlinedButton(
                        onClick = { navController?.navigate("registro_page") },
                        modifier = Modifier
                            .weight(1f)
                            .height(80.dp),
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = Color.White
                        ),
                        border = ButtonDefaults.outlinedButtonBorder.copy(width = 2.dp)
                    ) {
                        Text(
                            "Crear Cuenta",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Información adicional en landscape
                Row(
                    modifier = Modifier.fillMaxWidth(0.8f),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "📸",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            "Toma foto\nde evidencia",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "⭐",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            "Gana puntos\npor material",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            "🎁",
                            style = MaterialTheme.typography.headlineLarge,
                            modifier = Modifier.padding(bottom = 8.dp)
                        )
                        Text(
                            "Canjea por descuentos",
                            color = Color.White,
                            style = MaterialTheme.typography.bodyLarge,
                            textAlign = TextAlign.Center
                        )
                    }
                }


            }
        }
    }
}

@Preview(name = "Expanded", widthDp = 840, heightDp = 1200)
@Composable
fun PreviewExpanded() {
    HomeScreenExpandida()
}