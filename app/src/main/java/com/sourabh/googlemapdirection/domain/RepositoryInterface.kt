package com.sourabh.googlemapdirection.domain

import com.google.android.gms.maps.model.LatLng

interface RepositoryInterface {
    suspend fun getRoute(currentLocation: LatLng, destination: LatLng): Route
}