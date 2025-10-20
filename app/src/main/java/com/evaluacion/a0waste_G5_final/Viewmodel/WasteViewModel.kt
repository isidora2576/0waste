package com.evaluacion.a0waste_G5_final.Viewmodel


import androidx.lifecycle.ViewModel
import com.evaluacion.a0waste_G5_final.Navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import com.evaluacion.a0waste_G5_final.Navigation.NavigationEvent

open class WasteViewModel() : ViewModel() {

    private var userPoints = 0
    open fun addPoints(points: Int) {
        userPoints += points
    }
    open fun getPoints(): Int = userPoints

    private val _navigationEvents = MutableSharedFlow<NavigationEvent>()
    val navigationEvents = _navigationEvents.asSharedFlow()

    fun navigateTo(screen: Screen) {
        CoroutineScope(context = Dispatchers.Main).launch  {
            _navigationEvents.emit(NavigationEvent.NavigateTo(route = screen))
        }
    }

    fun navigateBack() {
        CoroutineScope(context = Dispatchers.Main).launch {
            _navigationEvents.emit(NavigationEvent.PopBackStack)
        }
    }

    fun navigateUp() {
        CoroutineScope(context = Dispatchers.Main).launch {
            _navigationEvents.emit(value = NavigationEvent.NavigateUp)
        }
    }
}
