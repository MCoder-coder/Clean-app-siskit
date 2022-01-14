package com.example.app_base_siskit.feature_map.domain.usecase

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.location.Location
import android.util.Log
import android.view.MotionEvent
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment.findNavController
import androidx.navigation.fragment.findNavController
import com.example.app_base_siskit.R
import com.example.app_base_siskit.feature_map.presentation.MapFragmentDirections
import org.mapsforge.map.android.view.MapView

class MapGetCordinatesFromGpsOnTapUseCase {
    fun invoke(context: Context ,e : MotionEvent, mapView: MapView, isInManualAddMode : Boolean) : Location{

        val manualLocation = Location("")

        if(isInManualAddMode) {
            Log.d(ContentValues.TAG, "onSingleTapConfirmed -> Click Acepted")
            val xpos = e.getX()
            val ypos = e.getY()

            val pr = mapView.mapViewProjection
            val clickLatlong = pr?.fromPixels(xpos.toDouble(), ypos.toDouble());
            if (clickLatlong != null) {
                mapView.setCenter(clickLatlong)
                //  addMarker(clickLatlong)

                manualLocation.setLatitude(clickLatlong.latitude)
                manualLocation.setLongitude(clickLatlong.longitude)
                // addNewGeoContactoManual(manualLocation)

                return manualLocation
            }
        }

        return manualLocation
    }
}
