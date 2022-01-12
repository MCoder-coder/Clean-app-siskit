package com.example.app_base_siskit.feature_map.domain.repository

import android.content.Context
import android.location.Location
import org.mapsforge.map.android.view.MapView

interface MapCoordinatesRepository {

    fun getCoordinatesFromPixel(mapView: MapView, mapPixelCordinates: Location) : Location

    fun getCoordinatesFromGps() : Location


}
