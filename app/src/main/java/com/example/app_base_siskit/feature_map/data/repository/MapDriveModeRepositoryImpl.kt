package com.example.app_base_siskit.feature_map.data.repository

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
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
    var inicialLatLong = LatLong(-37.4816786, -61.9454334)
    var myLocationOverlay: MyLocationOverlay? = null

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun mapDriveMode(context: Context, mapView: MapView)  {

        Log.d(ContentValues.TAG, "myLocationOverlay: " + myLocationOverlay)
        val myLocationMarker = context.resources.getDrawable(R.drawable.ic_baseline_my_location_24)
        val bitmap = AndroidGraphicFactory.convertToBitmap(myLocationMarker)
        val marker = Marker(inicialLatLong, bitmap, 0, 0)

        myLocationOverlay = MyLocationOverlay(
            context as Activity?, mapView.model?.mapViewPosition as MapViewPosition?,
            bitmap, null, null
        );
        mapView.layerManager?.layers?.add(myLocationOverlay);

        myLocationOverlay?.isSnapToLocationEnabled = true;
        myLocationOverlay?.enableMyLocation(true);
        val dm = myLocationOverlay?.driverMode
       Log.d(ContentValues.TAG, "driver mode: " + dm)
        if (dm == true) {
            myLocationOverlay?.driverMode = false
            Log.d(ContentValues.TAG, "Modo Conductor Desactivado " + dm)
            Toast.makeText(context, "Modo Conductor Desactivado", Toast.LENGTH_SHORT).show()
        }

        if (dm == false){
            Log.d(ContentValues.TAG, "Modo Conductor Activado " + dm)
            myLocationOverlay?.driverMode = true
            Toast.makeText(context, "Modo Conductor Activado", Toast.LENGTH_SHORT).show()
        }
        Log.d(ContentValues.TAG, "driver mode: " + dm)

    }
}