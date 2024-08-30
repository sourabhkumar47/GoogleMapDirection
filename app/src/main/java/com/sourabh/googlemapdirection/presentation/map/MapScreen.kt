package com.sourabh.googlemapdirection.presentation.map

import android.annotation.SuppressLint
import android.location.Geocoder
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Layers
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState
import com.sourabh.googlemapdirection.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.util.Locale

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MapScreen(
    mapsViewModel: MapsViewModel = viewModel()
) {
    val context = LocalContext.current
    val geocoder = Geocoder(context, Locale.getDefault())
    val uiSettings = remember { mutableStateOf(MapUiSettings(zoomControlsEnabled = false)) }
    val mapTypes = listOf(MapType.NORMAL, MapType.SATELLITE, MapType.HYBRID, MapType.TERRAIN)
    val expanded = remember { mutableStateOf(false) }
    val selectedMapType = remember { mutableStateOf(MapType.NORMAL) }
    val markerPosition = remember { mutableStateOf<LatLng?>(null) }
    val markerTitle = remember { mutableStateOf("Pinned Location") }
    val markerSnippet = remember { mutableStateOf("Marker for Pinned Location") }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(LatLng(22.799466859874325, 79.4524185367257), 4f)
    }

    // Function to get the location name from latitude and longitude
    suspend fun getLocationName(latLng: LatLng): String {
        return withContext(Dispatchers.IO) {
            val addresses = geocoder.getFromLocation(latLng.latitude, latLng.longitude, 1)
            addresses?.firstOrNull()?.getAddressLine(0) ?: "Unknown Location"
        }
    }

    Scaffold {
        Box(modifier = Modifier.fillMaxSize()) {
            GoogleMap(
                modifier = Modifier.padding(top = 22.dp, bottom = 22.dp),
                properties = mapsViewModel.state.mapProperties,
                uiSettings = uiSettings.value,
                cameraPositionState = cameraPositionState,
                onMapLongClick = { latLng ->
                    markerPosition.value = latLng
                }
            ) {
                markerPosition.value?.let { latLng ->
                    LaunchedEffect(latLng) {
                        val locationName = getLocationName(latLng)
                        markerTitle.value = locationName
                        markerSnippet.value = "Lat: ${latLng.latitude}, Lng: ${latLng.longitude}"
                    }
                    Marker(
                        state = rememberMarkerState(position = latLng).apply {
                            position = latLng
                        },
                        title = markerTitle.value,
                        snippet = markerSnippet.value,
                        draggable = false,
                        icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE)
                    )
                }
            }
            Box(modifier = Modifier.padding(start = 8.dp, top = 50.dp)) {
                Column {
                    Button(
                        onClick = { expanded.value = true },
                        colors = ButtonDefaults.buttonColors(colorResource(id = R.color.purple_200).copy(alpha = 0.4F)),
                    ) {
                        Icon(
                            imageVector = Icons.Default.Layers,
                            contentDescription = null,
                            tint = when (selectedMapType.value) {
                                MapType.NORMAL -> Color.Black
                                MapType.TERRAIN -> Color.Black
                                else -> Color.White
                            },
                            modifier = Modifier.size(30.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = expanded.value,
                        onDismissRequest = { expanded.value = false },
                    ) {
                        mapTypes.forEach { mapType ->
                            DropdownMenuItem(
                                { Text(text = mapType.name) },
                                onClick = {
                                    selectedMapType.value = mapType
                                    mapsViewModel.state = mapsViewModel.state.copy(
                                        mapProperties = mapsViewModel.state.mapProperties.copy(mapType = mapType)
                                    )
                                    expanded.value = false
                                })
                        }
                    }
                }
            }
        }
    }
}

@Preview(showSystemUi = true)
@Composable
fun MapScreenPreview() {
    MapScreen()
}