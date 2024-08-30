//package com.sourabh.googlemapdirection.data
//
//import com.google.android.gms.maps.model.LatLng
//import com.sourabh.googlemapdirection.domain.Route
//
//class MapRepositoryImpl(private val apiService: MapsApiService) : MapRepository {
//
//    override suspend fun getRoute(currentLocation: LatLng, destination: LatLng): Route {
//         Make a network call to the Google Directions API (or any other service) to get the route
//        val response = apiService.getRoute(
//            origin = "${currentLocation.latitude},${currentLocation.longitude}",
//            destination = "${destination.latitude},${destination.longitude}"
//        )
//
//        return Route(
//            distance = response.routes[0].legs[0].distance.text,
//            duration = response.routes[0].legs[0].duration.text,
//            polyline = response.routes[0].overview_polyline.points
//        )
//    }
//}