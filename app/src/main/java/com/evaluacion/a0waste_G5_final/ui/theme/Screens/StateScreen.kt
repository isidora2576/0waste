package com.evaluacion.a0waste_G5_final.ui.theme.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.evaluacion.a0waste_G5_final.Viewmodel.EstadoViewModel
import com.evaluacion.a0waste_G5_final.ui.theme.Components.EcoModeSwitch

@Composable
fun StateScreen(modifier: Modifier = Modifier) {
    val viewModel: EstadoViewModel = viewModel()

    val estado by viewModel.activo.collectAsState()

    // Loader inicial
    if (estado == null) {
        Box(
            modifier = modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                CircularProgressIndicator(color = Color(0xFF4CAF50))
                Spacer(modifier = Modifier.height(16.dp))
                Text("Cargando configuración eco...", color = Color(0xFF4CAF50))
            }
        }
    } else {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(32.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            EcoModeSwitch(viewModel = viewModel)
        }
    }
}