package com.example.app_base_siskit.feature_map.domain.repository

import android.content.Context
import org.mapsforge.map.android.view.MapView

interface MapLoadRepository {

    fun mapLoadLocalFile(context: Context, mapView: MapView)

}