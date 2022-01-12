package com.example.app_base_siskit.feature_map.domain.usecase

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.location.Location
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import androidx.navigation.Navigation
import com.example.app_base_siskit.R
import com.example.app_base_siskit.feature_map.domain.repository.MapCoordinatesRepository
import org.mapsforge.map.android.view.MapView
import javax.inject.Inject

class MapNewGeocontactManualyUseCase @Inject constructor(private val mapCoordinatesRepository: MapCoordinatesRepository) {



    fun invoke(context: Context , mapView: MapView, ev: MotionEvent) {

        val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                Log.d(ContentValues.TAG, "GestureDetector -> onSingleTapConfirmed")
                val xpos = e.getX()
                val ypos = e.getY()
                val mapPixelCordinates = Location("");
                mapPixelCordinates.latitude = ypos.toDouble();
                mapPixelCordinates.longitude = xpos.toDouble();

                val manualCordinates: Location = mapCoordinatesRepository.getCoordinatesFromPixel(mapView, mapPixelCordinates)
                Log.d(ContentValues.TAG, "manualCordinates: $manualCordinates")

                // TODO: navegar al formulario de geocontacto
                //
                Navigation.findNavController(context as Activity, R.id.nav_host_fragment ).navigate(
                    R.id.newClientFragment)

                Log.d(ContentValues.TAG, "ACTION_UP -> centrado al click")
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                super.onLongPress(e)
            }

            override fun onDoubleTap(e: MotionEvent): Boolean {
                return super.onDoubleTap(e)
            }
        });

        gestureDetector.onTouchEvent(ev);
    }

}