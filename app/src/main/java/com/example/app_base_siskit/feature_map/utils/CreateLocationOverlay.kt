package com.example.app_base_siskit.feature_map.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import com.example.app_base_siskit.R
import com.example.app_base_siskit.feature_map.MyLocationOverlay
import org.mapsforge.core.model.LatLong
import org.mapsforge.map.android.graphics.AndroidGraphicFactory
import org.mapsforge.map.android.view.MapView
import org.mapsforge.map.layer.overlay.Marker
import org.mapsforge.map.model.MapViewPosition

class CreateLocationOverlay {

    var myLocationOverlay: MyLocationOverlay? = null
    // Coordenada inicial para mostrar en el mapa
    var inicialLatLong = LatLong(-37.4816786, -61.9454334)

    @SuppressLint("UseCompatLoadingForDrawables")
    fun createLocationOverlay(context: Context, mapView : MapView, ){
        // marcador para mostrar ubicacion actual
        val myLocationMarker = context.getDrawable(R.drawable.ic_baseline_my_location_24)
        val bitmap = AndroidGraphicFactory.convertToBitmap(myLocationMarker)
        val marker = Marker(inicialLatLong, bitmap, 0, 0)

        myLocationOverlay = MyLocationOverlay((context as Activity), mapView.model?.mapViewPosition as MapViewPosition?,
            bitmap, null, null
        );
        mapView.layerManager?.layers?.add(myLocationOverlay);

        myLocationOverlay?.isSnapToLocationEnabled = true;
        myLocationOverlay?.enableMyLocation(true);
    }

}