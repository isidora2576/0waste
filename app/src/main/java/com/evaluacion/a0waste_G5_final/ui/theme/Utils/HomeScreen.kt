package com.evaluacion.a0waste_G5_final.ui.theme.Utils

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.evaluacion.a0waste_G5_final.R

// PANTALLA COMPACTA (Móviles pequeños)
@Composable
fun CompactHomeScreen(
    navController: NavController?,
    innerPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color(0xFF4CAF50))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "¡Bienvenido a 0Waste!",
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold
        )

        Text(
            text = "Recicla, gana puntos y ayuda al planeta",
            style = MaterialTheme.typography.bodyMedium,
            color = Color.White.copy(alpha = 0.9f)
        )

        Button(
            onClick = { navController?.navigate("scan_page") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.White,
                contentColor = Color(0xFF4CAF50)
            ),
            elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
        ) {
            Text("Escanear Residuos", fontWeight = FontWeight.Bold)
        }

        OutlinedButton(
            onClick = { navController?.navigate("centers_page") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White
            )
        ) {
            Text("Centros de Reciclaje", fontWeight = FontWeight.Bold)
        }

        OutlinedButton(
            onClick = { navController?.navigate("rewards_page") },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = Color.White
            )
        ) {
            Text("Mis Recompensas", fontWeight = FontWeight.Bold)
        }

        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo 0Waste",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.Fit
        )

        WasteItemsListCompact()
    }
}

// PANTALLA MEDIUM (Tablets pequeñas)
@Composable
fun MediumHomeScreen(
    navController: NavController?,
    innerPadding: PaddingValues
) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color(0xFF4CAF50))
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "¡Bienvenido a 0Waste!",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Recicla, gana puntos y ayuda al planeta",
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.White.copy(alpha = 0.9f)
                )
            }

            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo 0Waste",
                modifier = Modifier.size(100.dp)
            )
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { navController?.navigate("scan_page") },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF4CAF50)
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
            ) {
                Text("Escanear Residuos", fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = { navController?.navigate("centers_page") },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                )
            ) {
                Text("Centros Reciclaje", fontWeight = FontWeight.Bold)
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedButton(
                onClick = { navController?.navigate("rewards_page") },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                )
            ) {
                Text("Mis Recompensas", fontWeight = FontWeight.Bold)
            }

            OutlinedButton(
                onClick = { /* Estadísticas */ },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = Color.White
                )
            ) {
                Text("Mi Progreso", fontWeight = FontWeight.Bold)
            }
        }

        WasteItemsListMedium()
    }
}

// PANTALLA EXPANDED (Tablets grandes)
@Composable
fun ExpandedHomeScreen(
    navController: NavController?,
    innerPadding: PaddingValues
) {
    Row(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()
            .background(Color(0xFF4CAF50))
    ) {
        // Panel lateral
        Column(
            modifier = Modifier
                .width(300.dp)
                .fillMaxHeight()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "Logo 0Waste",
                modifier = Modifier.size(120.dp)
            )

            Text(
                text = "0Waste",
                style = MaterialTheme.typography.headlineLarge,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Sistema de reciclaje inteligente",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White.copy(alpha = 0.9f)
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { navController?.navigate("scan_page") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color(0xFF4CAF50)
                    ),
                    elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                ) {
                    Text("Escanear Residuos", fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = { navController?.navigate("centers_page") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Text("Centros de Reciclaje", fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = { navController?.navigate("rewards_page") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Text("Mis Recompensas", fontWeight = FontWeight.Bold)
                }

                OutlinedButton(
                    onClick = { /* Perfil */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = Color.White
                    )
                ) {
                    Text("Mi Perfil", fontWeight = FontWeight.Bold)
                }
            }
        }

        // Panel principal
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Materiales Reciclables",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.White,
                fontWeight = FontWeight.Bold
            )

            WasteItemsListExpanded()
        }
    }
}

// LISTAS ADAPTABLES CON COLORES COHERENTES
@Composable
fun WasteItemsListCompact() {
    val wasteItems = listOf(
        "Plástico - 1 punto",
        "Papel - 1 punto",
        "Aluminio - 2 puntos",
        "Vidrio - 2 puntos",
        "Metal - 3 puntos"

    )

    LazyColumn {
        items(wasteItems) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = item,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF4CAF50)
                )
            }
        }
    }
}

@Composable
fun WasteItemsListMedium() {
    val wasteItems = listOf(
        "Botellas PET - 1 punto",
        "Cajas de Cartón - 1 punto",
        "Latas de Aluminio - 2 puntos",
        "Botellas de Vidrio - 2 puntos",
        "Elementos de metal - 3 puntos"
    )

    LazyColumn {
        items(wasteItems) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = item,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF4CAF50)
                )
            }
        }
    }
}

@Composable
fun WasteItemsListExpanded() {
    val wasteItems = listOf(
        "Botellas PET - 1 punto - Ahorra 0.15kg CO2",
        "Cajas de Cartón - 1 punto - Ahorra 0.10kg CO2",
        "Latas de Aluminio - 2 puntos - Ahorra 0.25kg CO2",
        "Botellas de Vidrio - 2 puntos - Ahorra 0.20kg CO2",
        "Materiales de metal - 3 puntos - Ahorra 0.30kg CO2",
    )

    LazyColumn {
        items(wasteItems) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(8.dp)
            ) {
                Text(
                    text = item,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color(0xFF4CAF50)
                )
            }
        }
    }
}

// PREVIEWS
@Preview(showBackground = true, widthDp = 360, heightDp = 640)
@Composable
fun CompactHomeScreenPreview() {
    CompactHomeScreen(navController = null, innerPadding = PaddingValues())
}

@Preview(showBackground = true, widthDp = 600, heightDp = 800)
@Composable
fun MediumHomeScreenPreview() {
    MediumHomeScreen(navController = null, innerPadding = PaddingValues())
}

@Preview(showBackground = true, widthDp = 840, heightDp = 1000)
@Composable
fun ExpandedHomeScreenPreview() {
    ExpandedHomeScreen(navController = null, innerPadding = PaddingValues())
}