package com.example.app_base_siskit.feature_map.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.app_base_siskit.feature_map.MyLocationOverlay
import com.example.app_base_siskit.feature_map.domain.usecase.MapDownloadUseCase
import com.example.app_base_siskit.feature_map.domain.usecase.MapDriveModeUseCase
import com.example.app_base_siskit.feature_map.domain.usecase.MapManualNavigationModeUseCase
import com.example.app_base_siskit.feature_map.domain.usecase.MapLoadUseCase
import com.example.app_base_siskit.feature_map.utils.DirectoryPathVersionSdk
import com.example.app_base_siskit.feature_map.utils.MapDownloadNotification
import dagger.hilt.android.lifecycle.HiltViewModel
import org.mapsforge.map.android.view.MapView
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val mapLoadUseCase: MapLoadUseCase,
                                       private val mapDownloadUseCase: MapDownloadUseCase,
                                       private val mapDriverModeUseCase: MapDriveModeUseCase,
                                       private val mapManualNavigationModeUseCase: MapManualNavigationModeUseCase) : ViewModel(){

    //carga de mapa oflline
     fun mapLoad(context: Context , mapView: MapView){
        mapLoadUseCase.invoke(context, mapView)
     }

    //descarga del mapa
    fun mapDownload(context: Context){
        val id = 0L
        val myMapfile = DirectoryPathVersionSdk().getFile(context)
        val mapDownload = mapDownloadUseCase.invoke(context , id)
        if (!myMapfile.exists()){
            MapDownloadNotification(id).downloadDialog(context , mapDownload)
        }
    }

    fun mapManualNavigationModeMode(context: Context, mapView: MapView, isInManualAddMode : Boolean) {
         mapManualNavigationModeUseCase.invoke(context , mapView , isInManualAddMode)
    }

    fun mapDriveMode(context: Context , mapView: MapView) {
        mapDriverModeUseCase.invoke(context , mapView)
    }




}