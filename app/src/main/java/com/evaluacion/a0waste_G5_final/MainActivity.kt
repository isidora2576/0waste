package com.evaluacion.a0waste_G5_final

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material3.Scaffold
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.evaluacion.a0waste_G5_final.ui.Screens.HomeScreen
import com.evaluacion.a0waste_G5_final.Navigation.Screen
import com.evaluacion.a0waste_G5_final.ui.theme.Screens.ProfileScreen
import com.evaluacion.a0waste_G5_final.ui.theme.Screens.ScanScreen
import com.evaluacion.a0waste_G5_final.ui.theme.Screens.CentersScreen
import com.evaluacion.a0waste_G5_final.ui.theme.Screens.RewardsScreen
import com.evaluacion.a0waste_G5_final.Viewmodel.WasteViewModel
import com.evaluacion.a0waste_G5_final.Viewmodel.NavigationEvent
import com.evaluacion.a0waste_G5_final.ui.theme._0waste_G5Theme
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            _0waste_G5Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    _0waste_G5Theme {
        Greeting("Android")
    }
}