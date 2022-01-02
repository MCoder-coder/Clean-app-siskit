package com.example.app_base_siskit.feature_map.domain.repository

import android.content.Context
import org.mapsforge.map.android.view.MapView

interface MapManualNavigationModeRepository {
    fun gestureDetector(context: Context, mapView: MapView , isInManualAddMode : Boolean) : Boolean
}