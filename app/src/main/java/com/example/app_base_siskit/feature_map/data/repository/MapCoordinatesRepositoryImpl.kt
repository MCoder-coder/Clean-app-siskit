package com.example.app_base_siskit.feature_map.data.repository

import android.annotation.SuppressLint
import android.location.Location
import com.example.app_base_siskit.feature_map.domain.repository.MapCoordinatesRepository
import org.mapsforge.map.android.view.MapView


class MapCoordinatesRepositoryImpl : MapCoordinatesRepository {

    private var manualLocation: Location = Location("")
    private var gpsLocation: Location = Location("")

    @SuppressLint("ClickableViewAccessibility")
    override fun getCoordinatesFromPixel(mapView: MapView, mapPixelCordinates: Location): Location {

        val pr = mapView.mapViewProjection
        val clickLatlong = pr?.fromPixels(mapPixelCordinates.longitude, mapPixelCordinates.latitude);
        if(clickLatlong!= null){
            mapView.setCenter(clickLatlong)
           // addMarker(clickLatlong)
            manualLocation = Location("")
            manualLocation.latitude = clickLatlong.latitude
            manualLocation.longitude = clickLatlong.longitude
            //addNewGeoContactoManual(manualLocation)
        }

        return manualLocation
    }

    override fun getCoordinatesFromGps(): Location {
        gpsLocation = Location("")
        gpsLocation.latitude = 0.0
        gpsLocation.longitude = 0.0

        return gpsLocation
    }

}