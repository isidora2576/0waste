package com.evaluacion.a0waste_G5_final.Navigation

sealed class Screen(val route: String) {
    data object Home : Screen(route = "home_page")
    data object Scan : Screen(route = "scan_page")
    data object Centers : Screen(route = "centers_page")
    data object Rewards : Screen(route = "rewards_page")
    data object Profile : Screen(route = "profile_page")

    data object Registro : Screen(route = "registro_page")
    data object Resumen : Screen(route = "resumen_page")
    data object Login : Screen(route = "login_page")
    data object State : Screen(route = "state_page")
    data object Success : Screen(route = "success_page")

    data class Detail(val itemId: String) : Screen("detail_page/{itemId}"){
        fun buildRoute(): String {
            return route.replace("{itemId}", itemId)
        }
    }
}