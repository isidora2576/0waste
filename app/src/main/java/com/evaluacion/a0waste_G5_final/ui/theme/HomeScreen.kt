package com.evaluacion.a0waste_G5_final.ui.theme

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.evaluacion.a0waste_G5_final.ui.theme.Utils.rememberWindowSizeClass
import com.evaluacion.a0waste_G5_final.ui.theme.Utils.WindowType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController? = null) {
    val windowSize = rememberWindowSizeClass()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (windowSize.widthSizeClass == WindowType.COMPACT)
                            "0Waste"
                        else
                            "0Waste - Reciclaje Inteligente"
                    )
                }
            )
        }
    ) { innerPadding ->
        when (windowSize.widthSizeClass) {
            WindowType.COMPACT -> CompactHomeScreen(navController, innerPadding)
            WindowType.MEDIUM -> MediumHomeScreen(navController, innerPadding)
            WindowType.EXPANDED -> ExpandedHomeScreen(navController, innerPadding)
        }
    }
}

// PANTALLA COMPACTA (Móviles pequeños) - BASADO EN TU CÓDIGO ACTUAL
@Composable
fun CompactHomeScreen(
    navController: NavController?,
    innerPadding: PaddingValues
) {
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
                navController?.navigate("scan")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Escanear Residuos")
        }

        // Botón para centros de reciclaje
        Button(
            onClick = {
                navController?.navigate("centers")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Centros de Reciclaje")
        }

        // Botón para recompensas
        Button(
            onClick = {
                navController?.navigate("rewards")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Mis Recompensas")
        }

        // Imagen placeholder
        Image(
            painter = painterResource(id = android.R.drawable.ic_menu_gallery),
            contentDescription = "Logo 0Waste",
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp),
            contentScale = ContentScale.Fit
        )

        // Materiales reciclables (nuevo para la Guía 9)
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
                    style = MaterialTheme.typography.headlineLarge
                )
                Text(
                    text = "Recicla, gana puntos y ayuda al planeta",
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                contentDescription = "Logo 0Waste",
                modifier = Modifier.size(100.dp)
            )
        }

        // Botones en filas
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { navController?.navigate("scan") },
                modifier = Modifier.weight(1f)
            ) {
                Text("Escanear Residuos")
            }

            Button(
                onClick = { navController?.navigate("centers") },
                modifier = Modifier.weight(1f)
            ) {
                Text("Centros Reciclaje")
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(
                onClick = { navController?.navigate("rewards") },
                modifier = Modifier.weight(1f)
            ) {
                Text("Mis Recompensas")
            }

            // Botón extra para tablets
            Button(
                onClick = { /* Estadísticas */ },
                modifier = Modifier.weight(1f)
            ) {
                Text("Mi Progreso")
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
    ) {
        // Panel izquierdo - Navegación
        Column(
            modifier = Modifier
                .width(300.dp)
                .fillMaxHeight()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_gallery),
                contentDescription = "Logo 0Waste",
                modifier = Modifier.size(120.dp)
            )

            Text(
                text = "0Waste",
                style = MaterialTheme.typography.headlineLarge
            )

            Text(
                text = "Sistema de reciclaje inteligente",
                style = MaterialTheme.typography.bodyLarge
            )

            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    onClick = { navController?.navigate("scan") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Escanear Residuos")
                }

                Button(
                    onClick = { navController?.navigate("centers") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Centros de Reciclaje")
                }

                Button(
                    onClick = { navController?.navigate("rewards") },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Mis Recompensas")
                }

                Button(
                    onClick = { /* Perfil */ },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Mi Perfil")
                }
            }
        }

        // Panel derecho - Contenido
        Column(
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .padding(24.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Text(
                text = "Materiales Reciclables",
                style = MaterialTheme.typography.headlineMedium
            )

            WasteItemsListExpanded()
        }
    }
}

// LISTAS ADAPTABLES PARA MATERIALES (NUEVO PARA GUÍA 9)
@Composable
fun WasteItemsListCompact() {
    val wasteItems = listOf(
        "Plástico - 50 puntos",
        "Papel - 30 puntos",
        "Metal - 80 puntos",
        "Vidrio - 40 puntos"
    )

    LazyColumn {
        items(wasteItems) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = item,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Composable
fun WasteItemsListMedium() {
    val wasteItems = listOf(
        "Botellas PET - 50 puntos",
        "Cajas de Cartón - 30 puntos",
        "Latas de Aluminio - 80 puntos",
        "Botellas de Vidrio - 40 puntos"
    )

    LazyColumn {
        items(wasteItems) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = item,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

@Composable
fun WasteItemsListExpanded() {
    val wasteItems = listOf(
        "Botellas PET - 50 puntos - Ahorra 0.15kg CO2",
        "Cajas de Cartón - 30 puntos - Ahorra 0.10kg CO2",
        "Latas de Aluminio - 80 puntos - Ahorra 0.25kg CO2",
        "Botellas de Vidrio - 40 puntos - Ahorra 0.20kg CO2"
    )

    LazyColumn {
        items(wasteItems) { item ->
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
            ) {
                Text(
                    text = item,
                    modifier = Modifier.padding(16.dp),
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }
    }
}

// PREVIEWS PARA CADA TAMAÑO (GUÍA 9)
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

// Preview original que tenías
@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen()
}