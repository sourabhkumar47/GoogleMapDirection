package com.sourabh.googlemapdirection.presentation.map

import androidx.compose.foundation.layout.Box
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.maps.android.compose.MapType

@Composable
fun MapTypeDropDown(mapType: MapType, onMapTypeSelected: (MapType) -> Unit, expanded: Boolean) {
    val mapsViewModel = remember { MapsViewModel() }

    val mapTypes =
        listOf(
            MapType.NORMAL,
            MapType.SATELLITE,
            MapType.HYBRID,
            MapType.TERRAIN
        )
    val expanded = remember { mutableStateOf(false) }
    val selectedMapType = remember { mutableStateOf(MapType.HYBRID) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 54.dp)
    ) {
        Button(
            onClick = { expanded.value = true },
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            modifier = Modifier.align(Alignment.BottomStart)
        ) {
            Icon(
                imageVector = Icons.Default.Layers,
                contentDescription = null,
                tint = Color.Black,
                modifier = Modifier
                    .size(30.dp)
            )
        }

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
//                modifier = Modifier.align(Alignment.CenterStart)
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