package com.example.app_base_siskit.feature_map.presentation

import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.MotionEvent
import androidx.lifecycle.ViewModel
import com.example.app_base_siskit.feature_map.MyLocationOverlay
import com.example.app_base_siskit.feature_map.domain.usecase.*

import com.example.app_base_siskit.feature_map.utils.DirectoryPathVersionSdk
import dagger.hilt.android.lifecycle.HiltViewModel
import org.mapsforge.map.android.view.MapView
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val mapLoadUseCase: MapLoadUseCase,
                                       private val mapDownloadUseCase: MapDownloadUseCase,
                                       private val mapDriverModeUseCase: MapDriveModeUseCase,
                                       private val MapNewGeocontactManualyUseCase: MapNewGeocontactManualyUseCase,
                                       private val MapNewGeocontactGpsUseCase: MapNewGeocontactGpsUseCase) : ViewModel(){

    // TODO: borrar?
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

    fun mapDriveMode(context: Context , mapView: MapView , myLocationOverlay: MyLocationOverlay)  {
        return mapDriverModeUseCase.invoke(context , mapView , myLocationOverlay)
    }


    fun mapNewGeocontactManualy(context: Context, mapView: MapView, isInManualAddMode : Boolean, ev: MotionEvent ) : Boolean {


        if(isInManualAddMode) {
            Log.d(ContentValues.TAG, "Esta en Manual Mode -> Click Acepted")
            MapNewGeocontactManualyUseCase.invoke(context, mapView, ev)
            return true
        }else{
            Log.d(ContentValues.TAG, "Esta en Drive Mode -> Click ignored (Necesario estar en Manual Mode)")
            return false
        }

    }

    fun mapNewGeocontactFromGps(context: Context, mapView: MapView, isInManualAddMode : Boolean) : Boolean {

        if(!isInManualAddMode) {
            Log.d(ContentValues.TAG, "Esta en Drive Mode -> Click Acepted")
            MapNewGeocontactGpsUseCase.invoke()
            return true
        }else{
            Log.d(ContentValues.TAG, "Esta en Manual Mode -> Click ignored (Necesario estar en Drive Mode)")
            return false
        }

    }



}