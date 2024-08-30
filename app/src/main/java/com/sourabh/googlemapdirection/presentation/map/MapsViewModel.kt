package com.sourabh.googlemapdirection.presentation.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.sourabh.googlemapdirection.presentation.map.MapState

class MapsViewModel: ViewModel(){

    var state by mutableStateOf(MapState())


}