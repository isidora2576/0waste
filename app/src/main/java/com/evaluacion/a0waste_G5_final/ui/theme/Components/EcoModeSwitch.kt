package com.evaluacion.a0waste_G5_final.ui.theme.Components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Eco
import androidx.compose.material.icons.filled.Lightbulb
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.evaluacion.a0waste_G5_final.Viewmodel.EstadoViewModel


@Composable
fun EcoModeSwitch(viewModel: EstadoViewModel) {

    val estado by viewModel.activo.collectAsState()
    val mostrarMensaje by viewModel.mostrarMensaje.collectAsState()


    val colorFondo by androidx.compose.animation.animateColorAsState(
        targetValue = if (estado == true) Color(0xFF4CAF50) else Color(0xFF9E9E9E),
        animationSpec = tween(durationMillis = 500),
        label = "colorFondo"
    )


    val paddingAnimado by animateDpAsState(
        targetValue = if (estado == true) 28.dp else 4.dp,
        animationSpec = tween(durationMillis = 300),
        label = "paddingInterruptor"
    )


    val textoBoton = if (estado == true) "Desactivar Modo Eco" else "Activar Modo Eco"

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        colors = CardDefaults.cardColors(containerColor = colorFondo)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Modo Eco Activado",
                color = Color.White,
                style = MaterialTheme.typography.titleMedium
            )

            Spacer(modifier = Modifier.height(16.dp))


            Box(
                modifier = Modifier
                    .width(60.dp)
                    .height(34.dp)
                    .clip(RoundedCornerShape(17.dp))
                    .background(Color.White.copy(alpha = 0.3f))
            ) {
                Box(
                    modifier = Modifier
                        .size(26.dp)
                        .offset(x = paddingAnimado, y = 4.dp)
                        .clip(RoundedCornerShape(13.dp))
                        .background(Color.White),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (estado == true) Icons.Default.Eco else Icons.Default.Lightbulb,
                        contentDescription = "Estado",
                        tint = colorFondo,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {

                    viewModel.alternarEstado()
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = textoBoton,
                    color = colorFondo,
                    style = MaterialTheme.typography.labelLarge
                )
            }


            AnimatedVisibility(
                visible = mostrarMensaje,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) {
                Text(
                    text = "¡Modo Eco ${if (estado == true) "activado" else "desactivado"}!",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }
        }
    }
}