package com.evaluacion.a0waste_G5_final.ui.theme.Utils

import androidx.activity.compose.LocalActivityResultRegistryOwner
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.Composable


@OptIn(markerClass = [ExperimentalMaterial3WindowSizeClassApi::class])
@Composable
fun obtenerWindowSizeClass(): WindowSizeClass{
    return calculateWindowSizeClass(LocalActivityResultRegistryOwner.current as android.app.Activity)
}