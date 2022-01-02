package com.example.app_base_siskit.feature_map.domain.usecase

import android.content.Context
import com.example.app_base_siskit.feature_map.domain.repository.MapManualNavigationModeRepository
import org.mapsforge.map.android.view.MapView
import javax.inject.Inject

class MapManualNavigationModeUseCase @Inject constructor(private val mapManualNavigationModeRepository: MapManualNavigationModeRepository) {

    fun invoke(context: Context , mapView: MapView , isInManualAddMode : Boolean) : Boolean{
        return mapManualNavigationModeRepository.gestureDetector(context , mapView , isInManualAddMode)
    }

}