//package com.sourabh.googlemapdirection.presentation
//
//import androidx.lifecycle.ViewModel
//import androidx.lifecycle.viewModelScope
//import com.google.android.gms.maps.model.LatLng
//import com.sourabh.googlemapdirection.domain.Route
//import com.sourabh.googlemapdirection.useCase.GetRouteUseCase
//import com.sourabh.googlemapdirection.useCase.UpdateRouteUseCase
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//import kotlinx.coroutines.launch
//
//class MapsViewModel(
//    private val getRouteUseCase: GetRouteUseCase,
//    private val updateRouteUseCase: UpdateRouteUseCase
//) : ViewModel() {
//
//    private val _routeState = MutableStateFlow<Route?>(null)
//    val routeState: StateFlow<Route?> = _routeState
//
//    fun getRoute(currentLocation: LatLng, destination: LatLng) {
//        viewModelScope.launch {
//            val route = getRouteUseCase.execute(currentLocation, destination)
//            _routeState.value = route
//        }
//    }
//
//    fun updateRoute(currentLocation: LatLng, destination: LatLng) {
//        viewModelScope.launch {
//            val route = updateRouteUseCase.execute(currentLocation, destination)
//            _routeState.value = route
//        }
//    }
//}