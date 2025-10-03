package com.evaluacion.a0waste_G5_final.ui.theme.Utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp

data class WindowSizeClass(
    val widthSizeClass: WindowType,
    val heightSizeClass: WindowType
)

enum class WindowType { COMPACT, MEDIUM, EXPANDED }

@Composable
fun rememberWindowSizeClass(): WindowSizeClass {
    val config = LocalConfiguration.current
    val width = config.screenWidthDp.dp
    val height = config.screenHeightDp.dp

    val widthWindowType = when {
        width < 600.dp -> WindowType.COMPACT
        width < 840.dp -> WindowType.MEDIUM
        else -> WindowType.EXPANDED
    }

    val heightWindowType = when {
        height < 480.dp -> WindowType.COMPACT
        height < 900.dp -> WindowType.MEDIUM
        else -> WindowType.EXPANDED
    }

    return WindowSizeClass(widthWindowType, heightWindowType)
}