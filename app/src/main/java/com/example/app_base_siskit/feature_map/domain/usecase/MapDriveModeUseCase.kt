package com.example.app_base_siskit.feature_map.domain.usecase

import android.content.Context
import com.example.app_base_siskit.feature_map.domain.repository.MapDriveModeRepository
import org.mapsforge.map.android.view.MapView
import javax.inject.Inject

class MapDriveModeUseCase @Inject constructor(private val mapDriveModeRepository: MapDriveModeRepository){
    fun invoke(context: Context, mapView: MapView) {
         mapDriveModeRepository.mapDriveMode(context , mapView)
    }
}