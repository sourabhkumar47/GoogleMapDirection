package com.sourabh.googlemapdirection.presentation

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.sourabh.googlemapdirection.location.LocationUtils
import com.sourabh.googlemapdirection.presentation.map.MapScreen

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val activity = context as Activity

    var location by remember { mutableStateOf<String?>(null) }
    var hasLocationPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context, Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    LaunchedEffect(key1 = hasLocationPermission) {
        if (hasLocationPermission) {
            location = LocationUtils().getCurrentLocation(activity)
        } else {
            ActivityCompat.requestPermissions(
                activity, arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                ), LocationUtils.LOCATION_PERMISSION_REQUEST_CODE
            )
        }
    }

    MapScreen()
}