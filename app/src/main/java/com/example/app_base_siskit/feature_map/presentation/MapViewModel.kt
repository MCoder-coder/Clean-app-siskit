package com.example.app_base_siskit.feature_map.presentation

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.lifecycle.ViewModel
import com.example.app_base_siskit.feature_map.utils.MyLocationOverlay
import com.example.app_base_siskit.feature_map.domain.usecase.*

import com.example.app_base_siskit.feature_map.utils.DirectoryPathVersionSdk
import com.example.app_base_siskit.feature_map.utils.LocationDataClass
import dagger.hilt.android.lifecycle.HiltViewModel
import org.mapsforge.map.android.view.MapView
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val mapLoadUseCase: MapLoadUseCase,
                                       private val mapDownloadUseCase: MapDownloadUseCase,
                                       private val mapGetCordinatesFromGpsOnTapUseCase: MapGetCordinatesFromGpsOnTapUseCase
                                    ) : ViewModel(){



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

    fun mapDriveMode(context: Context, mapView: MapView , myLocationOverlay : MyLocationOverlay) {

        Log.d(ContentValues.TAG, "myLocationOverlay: " + myLocationOverlay)
        //Log.d(TAG, "driver mode: " + //dm)
        val dm = myLocationOverlay.driverMode
        Log.d(ContentValues.TAG, "driver mode: " + dm)
        if (dm == true) {
            myLocationOverlay.driverMode = false
            Toast.makeText(context, "Modo Conductor Desactivado", Toast.LENGTH_SHORT).show()
        } else {
            myLocationOverlay.driverMode = true
            Toast.makeText(context, "Modo Conductor Activado", Toast.LENGTH_SHORT).show()
        }

    }

    fun mapGetCordinatesFromGpsOnTapUseCase(context: Context, e : MotionEvent, mapView: MapView, isInManualAddMode : Boolean) : LocationDataClass{
        return  mapGetCordinatesFromGpsOnTapUseCase.invoke(context , e , mapView , isInManualAddMode)
    }



}