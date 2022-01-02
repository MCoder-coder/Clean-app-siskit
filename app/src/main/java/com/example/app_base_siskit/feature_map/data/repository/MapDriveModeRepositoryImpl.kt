package com.example.app_base_siskit.feature_map.data.repository

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import com.example.app_base_siskit.R
import com.example.app_base_siskit.feature_map.MyLocationOverlay
import com.example.app_base_siskit.feature_map.domain.repository.MapDriveModeRepository
import org.mapsforge.core.model.LatLong
import org.mapsforge.map.android.graphics.AndroidGraphicFactory
import org.mapsforge.map.android.view.MapView
import org.mapsforge.map.layer.overlay.Marker
import org.mapsforge.map.model.MapViewPosition

class MapDriveModeRepositoryImpl : MapDriveModeRepository {
    private var inicialLatLong = LatLong(-37.4816786, -61.9454334)
   //0 private lateinit var myLocationOverlay: MyLocationOverlay
    @SuppressLint("UseCompatLoadingForDrawables", "LongLogTag")
    override fun mapDriveMode(context: Context, mapView: MapView , myLocationOverlay : MyLocationOverlay)   {

        Log.d(TAG, "myLocationOverlay: " + myLocationOverlay)
        //Log.d(TAG, "driver mode: " + //dm)
        val dm = myLocationOverlay?.getDriverMode()
        Log.d(TAG, "driver mode: " + dm)
        if (dm == true) {
            myLocationOverlay?.setDriverMode(false)
            Toast.makeText(context, "Modo Conductor Desactivado", Toast.LENGTH_SHORT).show()
        } else {
            myLocationOverlay?.setDriverMode(true)
            Toast.makeText(context, "Modo Conductor Activado", Toast.LENGTH_SHORT).show()
        }


    }


}