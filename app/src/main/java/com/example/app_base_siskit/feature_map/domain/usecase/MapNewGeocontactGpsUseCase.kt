package com.example.app_base_siskit.feature_map.domain.usecase

import android.content.ContentValues
import android.content.Context
import android.location.Location
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import com.example.app_base_siskit.feature_map.domain.repository.MapCoordinatesRepository
import org.mapsforge.map.android.view.MapView
import javax.inject.Inject

class MapNewGeocontactGpsUseCase @Inject constructor(private val mapCoordinatesRepository: MapCoordinatesRepository) {



    fun invoke() {

        val gpsCordinates: Location = mapCoordinatesRepository.getCoordinatesFromGps()
        Log.d(ContentValues.TAG, "gpsCordinates: $gpsCordinates")

        // TODO: navegar al formulario de geocontacto
        //

    }

}