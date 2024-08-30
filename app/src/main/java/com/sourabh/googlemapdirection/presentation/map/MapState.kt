package com.sourabh.googlemapdirection.presentation.map

import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType

data class MapState(

    val mapProperties: MapProperties = MapProperties(
        isMyLocationEnabled = true,
        isTrafficEnabled = true,
        isBuildingEnabled = true,
        mapType = MapType.NORMAL
    ),
)