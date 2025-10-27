package com.evaluacion.a0waste_G5_final.ui.theme.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.evaluacion.a0waste_G5_final.Viewmodel.WasteViewModel
import kotlinx.coroutines.launch

// Datos para el menú del drawer
data class DrawerItem(
    val title: String,
    val route: String,
    val icon: androidx.compose.ui.graphics.vector.ImageVector
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenCompacta(
    navController: NavController? = null,
    viewModel: WasteViewModel? = null
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    // Items del menú lateral
    val menuItems = listOf(
        DrawerItem("Inicio", "home_page", Icons.Default.Home),
        DrawerItem("Escanear Materiales", "scan_page", Icons.Default.Camera),
        DrawerItem("Mis Recompensas", "rewards_page", Icons.Default.CardGiftcard),
        DrawerItem("Centros de Reciclaje", "centers_page", Icons.Default.LocationOn),
        DrawerItem("Mi Perfil", "profile_page", Icons.Default.Person),
        DrawerItem("Ver Animaciones", "state_page", Icons.Default.Animation),
        DrawerItem("Cerrar Sesión", "login_page", Icons.Default.Logout)
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier.width(280.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color(0xFF4CAF50))
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo 0Waste",
                        modifier = Modifier.size(70.dp),
                        contentScale = ContentScale.Fit
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "0Waste",
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "Transforma tu reciclaje",
                        color = Color.White.copy(alpha = 0.8f),
                        fontSize = 12.sp
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "${viewModel?.puntosUsuario?.value ?: 0} puntos",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // Items del menú
                LazyColumn(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    items(menuItems) { item ->
                        NavigationDrawerItem(
                            label = {
                                Text(
                                    text = item.title,
                                    style = MaterialTheme.typography.bodyMedium
                                )
                            },
                            selected = false,
                            onClick = {
                                // Cerrar drawer primero
                                scope.launch {
                                    drawerState.close()
                                }
                                // Navegar a la pantalla correspondiente
                                when (item.route) {
                                    "login_page" -> {
                                        // Cerrar sesión - volver al login
                                        navController?.navigate(item.route) {
                                            popUpTo("home_page") { inclusive = true }
                                        }
                                    }
                                    else -> {
                                        navController?.navigate(item.route)
                                    }
                                }
                            },
                            icon = {
                                Icon(
                                    imageVector = item.icon,
                                    contentDescription = item.title
                                )
                            },
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                    }
                }
            }
        }
    ) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "0Waste",
                            color = Color.White,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFF81C784)
                    ),
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                scope.launch {
                                    drawerState.open()
                                }
                            }
                        ) {
                            Icon(
                                Icons.Default.Menu,
                                contentDescription = "Abrir menú",
                                tint = Color.White
                            )
                        }
                    }
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
                        .padding(32.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.logo),
                        contentDescription = "Logo 0Waste",
                        modifier = Modifier
                            .height(240.dp)
                            .padding(bottom = 40.dp),
                        contentScale = ContentScale.Fit
                    )

                    Text(
                        text = "Transforma tu reciclaje en recompensas",
                        color = Color.White,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    Text(
                        text = "Toma fotos de tus materiales reciclables y gana puntos",
                        color = Color.White.copy(alpha = 0.8f),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 40.dp)
                    )

                    Button(
                        onClick = { navController?.navigate("scan_page") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color(0xFF4CAF50)
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp)
                    ) {
                        Text(
                            text = "Escanear Materiales",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = { navController?.navigate("rewards_page") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Text(
                            text = "Ver Recompensas",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedButton(
                        onClick = { navController?.navigate("centers_page") },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                    ) {
                        Text(
                            text = "Centros de Reciclaje",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(32.dp))

                    Text(
                        text = "Cada foto vale puntos • Canjea por descuentos",
                        color = Color.White.copy(alpha = 0.7f),
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

@Preview(name = "Compact", widthDp = 360, heightDp = 800)
@Composable
fun PreviewCompact() {
    HomeScreenCompacta()
}