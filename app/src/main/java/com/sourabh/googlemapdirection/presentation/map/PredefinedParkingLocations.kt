package com.sourabh.googlemapdirection.presentation.map

import com.google.android.gms.maps.model.LatLng

data class PredefinedParkingLocations(
    val id: Int,
    val name: String,
    val location: LatLng,
)

val predefinedParkingLocations = listOf(
    PredefinedParkingLocations(
        id = 1,
        name = "Location 1",
        location = LatLng(54.5744, -1.234),
    ),
    PredefinedParkingLocations(
        id = 2,
        name = "Location 2",
        location = LatLng(54.5744, -1.234),
    ),
    PredefinedParkingLocations(
        id = 3,
        name = "Location 3",
        location = LatLng(54.5744, -1.234),
    ),
    PredefinedParkingLocations(
        id = 4,
        name = "Location 4",
        location = LatLng(54.5744, -1.234),
    ),
)

data class ParkingCities(
    val lng: Double
)
//
//fun addAllPositions() = listOf(
//
//    AllPositions("Stockholm",59.3252671500249, 18.06892284219277),
//    AllPositions("Berlin",52.5210668493763, 13.437903189993865),
//    AllPositions("Warszawa",52.215711901711245, 20.996237421187725),
//    AllPositions("Paris",48.84465430007765, 2.270300780605712)
//)