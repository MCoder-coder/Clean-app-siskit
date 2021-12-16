package com.example.app_base_siskit.feature_map.domain.repository

import android.content.Context
import com.example.app_base_siskit.feature_map.MyLocationOverlay
import org.mapsforge.map.android.view.MapView

interface MapDriveModeRepository {
    fun mapDriveMode(context: Context , mapView: MapView )
}