package com.example.app_base_siskit.feature_map.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.app_base_siskit.feature_map.domain.usecase.MapDownloadUseCase
import com.example.app_base_siskit.feature_map.domain.usecase.MapLoadUseCase
import com.example.app_base_siskit.feature_map.utils.DirectoryPathVersionSdk
import com.example.app_base_siskit.feature_map.utils.MapDownloadNotification
import dagger.hilt.android.lifecycle.HiltViewModel
import org.mapsforge.map.android.view.MapView
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val mapLoadUseCase: MapLoadUseCase , private val mapDownloadUseCase: MapDownloadUseCase) : ViewModel(){

    //carga de mapa oflline
     fun mapLoad(context: Context , mapView: MapView){
        mapLoadUseCase.invoke(context, mapView)
    }

    //descarga del mapa
    fun mapDownload(context: Context){
        val myMapfile = DirectoryPathVersionSdk().getFile(context)
        val mapDownload = mapDownloadUseCase.invoke(context)
        if (!myMapfile.exists()){
            MapDownloadNotification().downloadDialog(context , mapDownload)
        }
    }
}