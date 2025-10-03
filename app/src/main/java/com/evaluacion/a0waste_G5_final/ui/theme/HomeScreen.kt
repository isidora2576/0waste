package com.evaluacion.a0waste_G5_final.ui.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
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
fun HomeScreen(
    navController: NavController? = null,
    windowSizeClass: WindowSizeClass
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = when (windowSizeClass.widthSizeClass) {
                            WindowWidthSizeClass.Compact -> "0Waste"
                            WindowWidthSizeClass.Medium -> "0Waste Reciclaje"
                            else -> "0Waste - Reciclaje Inteligente"
                        }
                    )
                }
            )
        }
    ) { innerPadding ->
        when (windowSizeClass.widthSizeClass) {
            WindowWidthSizeClass.Compact -> CompactHomeScreen(navController, innerPadding)
            WindowWidthSizeClass.Medium -> MediumHomeScreen(navController, innerPadding)
            else -> ExpandedHomeScreen(navController, innerPadding)
        }
    }
}

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

        Image(
            painter = painterResource(id = android.R.drawable.ic_menu_gallery),
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

// LISTAS ADAPTABLES
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