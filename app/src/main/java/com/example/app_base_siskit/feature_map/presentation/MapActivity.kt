package com.example.app_base_siskit.feature_map.presentation

import android.content.Context
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.*
import android.widget.ImageButton
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.app_base_siskit.R
import org.mapsforge.core.model.LatLong
import org.mapsforge.map.android.graphics.AndroidGraphicFactory
import org.mapsforge.map.android.graphics.AndroidGraphicFactory.convertToBitmap
import org.mapsforge.map.android.layers.MyLocationOverlay
import org.mapsforge.map.android.util.AndroidUtil
import org.mapsforge.map.android.view.MapView
import org.mapsforge.map.layer.cache.TileCache
import org.mapsforge.map.layer.overlay.Marker
import org.mapsforge.map.layer.renderer.TileRendererLayer
import org.mapsforge.map.model.MapViewPosition

class MapActivity : AppCompatActivity() {

    // VARIABLES GENERALES
    var TAG = "RAC"
    var mapView: MapView? = null
    private val REQUEST_PERMISSION_LOCATION = 10
    private val REQUEST_PERMISSION_EXTERNALSTORAGE = 20
    var map: org.osmdroid.views.MapView? = null
    lateinit var locationManager: LocationManager

    // CONTROLES (Botones, etc)
    var btnDriveMode: ImageButton? = null
    var btnAddBacheGPS: ImageButton? = null
    var btnAddBacheClick: ImageButton? = null

    // VARIABLES MAPA
    var tileCache: TileCache? = null
    var tileRendererLayer: TileRendererLayer? = null
    var myLocationOverlay: MyLocationOverlay? = null
    // Coordenada inicial para mostrar en el mapa
    var inicialLatLong = LatLong(-37.4816786, -61.9454334)

    // variables para gestos en el mapa
    lateinit var gestureDetector : GestureDetector
    var isInManualAddMode: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        // Ubicacion: inicializo el objeto locationManager
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager

        // Mapsforge
        AndroidGraphicFactory.createInstance(application);
        mapView = findViewById <MapView> (R.id.map)
        mapView?.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mapView?.setClickable(true);

        // create a tile cache of suitable size (tile = fragmento del mapa renderizado en imagen cuadrada)
        tileCache = AndroidUtil.createTileCache(this, "mapcache",
            mapView?.getModel()?.displayModel?.getTileSize() ?: 256, 1f,
            mapView?.getModel()?.frameBufferModel?.getOverdrawFactor() ?: 1.0 )

        mapView?.getModel()?.mapViewPosition?.zoomLevel = 15.toByte()
        mapView?.getMapZoomControls()?.zoomLevelMin = 14.toByte()
        mapView?.getMapZoomControls()?.zoomLevelMax = 20.toByte()



        //mapView?.setClickable(true);
        mapView?.setBuiltInZoomControls(true);
        mapView?.getMapScaleBar()?.setVisible(false);

        // Centro el mapa a las coordenadas
        mapView?.getModel()?.mapViewPosition?.setCenter(inicialLatLong);


    }




}