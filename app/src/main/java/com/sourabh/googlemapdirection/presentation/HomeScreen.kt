package com.sourabh.googlemapdirection.presentation

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import com.sourabh.googlemapdirection.location.LocationUtils
import com.sourabh.googlemapdirection.presentation.map.MapScreen

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    //add background and redirect to map screen

    val focusManager = LocalFocusManager.current

    var location by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(key1 = Unit) {
        location = LocationUtils().getCurrentLocation(context as Activity)
    }
    MapScreen()

}