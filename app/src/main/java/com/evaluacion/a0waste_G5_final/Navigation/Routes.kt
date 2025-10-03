package com.evaluacion.a0waste_G5_final.Navigation

sealed class Screen(val route: String) {
    data object Home : Screen(route = "home_page")
    data object Scan : Screen(route = "scan_page")
    data object Centers : Screen(route = "centers_page")
    data object Rewards : Screen(route = "rewards_page")
    data object Profile : Screen(route = "profile_page")
}