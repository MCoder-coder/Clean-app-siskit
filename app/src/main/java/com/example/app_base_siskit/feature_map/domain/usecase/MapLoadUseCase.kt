package com.example.app_base_siskit.feature_map.domain.usecase

import android.content.Context
import com.example.app_base_siskit.feature_map.domain.repository.MapLoadRepository
import org.mapsforge.map.android.view.MapView
import javax.inject.Inject


class MapLoadUseCase @Inject constructor(private var mapLoadRepository : MapLoadRepository){

    fun invoke( context: Context ,  mapView: MapView)  {
        return mapLoadRepository.mapLoadLocalFile(context, mapView)
    }
}