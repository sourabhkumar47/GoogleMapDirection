//package com.sourabh.googlemapdirection.useCase
//
//import com.google.android.gms.maps.model.LatLng
//import com.sourabh.googlemapdirection.domain.Route
//
//class GetRouteUseCase(private val repository: MapRepository) {
//
//    suspend fun execute(currentLocation: LatLng, destination: LatLng): Route {
//        return repository.getRoute(currentLocation, destination)
//    }
//}
//
//class UpdateRouteUseCase(private val repository: MapRepository) {
//
//    suspend fun execute(currentLocation: LatLng, destination: LatLng): Route {
//        return repository.getRoute(currentLocation, destination)
//    }
//}