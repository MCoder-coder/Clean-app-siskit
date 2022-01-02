package com.example.app_base_siskit.feature_map.presentation

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.app_base_siskit.feature_map.MyLocationOverlay

import com.example.app_base_siskit.feature_map.domain.usecase.MapDownloadUseCase
import com.example.app_base_siskit.feature_map.domain.usecase.MapDriveModeUseCase
import com.example.app_base_siskit.feature_map.domain.usecase.MapManualNavigationModeUseCase
import com.example.app_base_siskit.feature_map.domain.usecase.MapLoadUseCase
import com.example.app_base_siskit.feature_map.utils.DirectoryPathVersionSdk
import dagger.hilt.android.lifecycle.HiltViewModel
import org.mapsforge.map.android.view.MapView
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val mapLoadUseCase: MapLoadUseCase,
                                       private val mapDownloadUseCase: MapDownloadUseCase,
                                       private val mapDriverModeUseCase: MapDriveModeUseCase,
                                       private val mapManualNavigationModeUseCase: MapManualNavigationModeUseCase) : ViewModel(){

    var myLocationOverlay: MyLocationOverlay? = null

    //carga de mapa oflline
     fun mapLoad(context: Context , mapView: MapView){
        mapLoadUseCase.invoke(context, mapView)
     }

    //descarga del mapa
    fun mapDownload(context: Context){
        val myMapfile = DirectoryPathVersionSdk().getFile(context)
        if (!myMapfile.exists()){
            mapDownloadUseCase.invoke(context)
            Log.i("Tag no existe" , "si no existe descargo")
        }else{
            Log.i("Tag Existe " , "no hago nada")
        }

    }

    fun mapManualNavigationMode(context: Context, mapView: MapView, isInManualAddMode : Boolean) : Boolean {
         return mapManualNavigationModeUseCase.invoke(context , mapView , isInManualAddMode)
    }

    fun mapDriveMode(context: Context , mapView: MapView , myLocationOverlay: MyLocationOverlay)  {
         return mapDriverModeUseCase.invoke(context , mapView , myLocationOverlay)
    }




}