package com.example.app_base_siskit.feature_map.domain.repository

import android.content.Context
import android.view.GestureDetector
import com.example.app_base_siskit.feature_map.MyLocationOverlay
import org.mapsforge.map.android.view.MapView

interface MapManualNavigationModeRepository {
    fun gestureDetector(context: Context, mapView: MapView , isInManualAddMode : Boolean)
}