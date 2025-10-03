package com.evaluacion.a0waste_G5_final.ui.theme.Screens

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import com.evaluacion.a0waste_G5_final.ui.*

@Composable
fun HomeScreen2() {
    val windowSizeClass = obtenerWindowSizeClass()
    when (windowSizeClass.widthSizeClass) {
        WindowWidthSizeClass.Compact -> HomeScreenCompacta()
        WindowWidthSizeClass.Expanded -> HomeScreenExpandida()
        WindowWidthSizeClass.Medium -> HomeScreenMediana()
    }
}