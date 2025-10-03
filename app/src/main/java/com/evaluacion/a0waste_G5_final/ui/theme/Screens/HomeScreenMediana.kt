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
import com.evaluacion.a0waste_G5_final.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenMediana() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "0Waste, Reciclar y ahorrar es posible",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp // Título más grande
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
                    .fillMaxSize()
                    .padding(48.dp), // Más padding en tablets
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Logo 0Waste",
                    modifier = Modifier
                        .height(320.dp) // Logo más grande
                        .padding(bottom = 50.dp),
                    contentScale = ContentScale.Fit
                )

                Text(
                    text = "Bienvenido a 0Waste",
                    color = Color.White,
                    style = MaterialTheme.typography.displayLarge, // Texto más grande
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 12.dp)
                )

                Button(
                    onClick = { /* acción iniciar sesión */ },
                    modifier = Modifier
                        .fillMaxWidth(0.8f) // Botón un poco más angosto
                        .height(60.dp), // Botón más alto
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF4CAF50)
                    ),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 8.dp
                    )
                ) {
                    Text(
                        text = "Iniciar sesión",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp // Texto más grande
                    )
                }

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedButton(
                    onClick = { /* acción crear cuenta */ },
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(60.dp),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    ),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        width = 2.dp
                    )
                ) {
                    Text(
                        text = "Crear cuenta",
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )
                }

                Spacer(modifier = Modifier.height(40.dp))
                Text(
                    text = "Recicla y consigue descuentos",
                    color = Color.White.copy(alpha = 0.7f),
                    style = MaterialTheme.typography.bodyLarge, // Texto más grande
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview(name = "Medium", widthDp = 600, heightDp = 960)
@Composable
fun PreviewMedium() {
    HomeScreenMediana()
}