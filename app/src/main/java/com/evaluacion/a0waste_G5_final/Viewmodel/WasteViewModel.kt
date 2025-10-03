package com.evaluacion.a0waste_G5_final.Viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.evaluacion.a0waste_G5_final.Navigation.Screen
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class WasteViewModel : ViewModel() {

    private var userPoints = 0

    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    fun addPoints(points: Int) {
        userPoints += points
    }

    fun getPoints(): Int = userPoints

    fun navigateTo(screen: Screen) {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.NavigateTo(route = screen))
        }
    }

    fun navigateBack() {
        viewModelScope.launch {
            _navigationEvents.emit(NavigationEvent.PopBackStack)
        }
    }
}
sealed class NavigationEvent {
    data class NavigateTo(val route: Screen) : NavigationEvent()
    object PopBackStack : NavigationEvent()
    object NavigateUp : NavigationEvent()
}