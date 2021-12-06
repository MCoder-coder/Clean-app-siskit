package com.example.app_base_siskit.feature_map.presentation

import android.Manifest
import android.content.Context
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.preference.PreferenceManager
import android.util.Log
import android.view.*
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import android.widget.Toolbar
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.app_base_siskit.BuildConfig
import com.example.app_base_siskit.MainActivity
import com.example.app_base_siskit.R
import dagger.hilt.android.internal.Contexts.getApplication
import org.mapsforge.core.model.LatLong
import org.mapsforge.map.android.graphics.AndroidGraphicFactory
import org.mapsforge.map.android.layers.MyLocationOverlay
import org.mapsforge.map.android.util.AndroidUtil
import org.mapsforge.map.android.view.MapView
import org.mapsforge.map.layer.cache.TileCache
import org.mapsforge.map.layer.overlay.Marker
import org.mapsforge.map.layer.renderer.TileRendererLayer
import org.mapsforge.map.model.MapViewPosition
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay


class MapFragment : Fragment() {



    // VARIABLES GENERALES
    var TAG = "RAC"
    lateinit var mapView: MapView
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_map, container, false)

        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        AndroidGraphicFactory.createInstance(activity?.application);

        // Mapsforge
        //AndroidGraphicFactory.createInstance(getApplication());


        mapView = view.findViewById(R.id.map)


        //load/initialize the osmdroid configuration, this can be done
        mapView?.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mapView?.isClickable = true;

        tileCache = AndroidUtil.createTileCache(
            activity?.application, "mapcache",
            mapView?.model?.displayModel?.tileSize ?: 256, 1f,
            mapView?.model?.frameBufferModel?.overdrawFactor ?: 1.0 )

        mapView?.model?.mapViewPosition?.zoomLevel = 15.toByte()
        mapView?.mapZoomControls?.zoomLevelMin = 14.toByte()
        mapView?.mapZoomControls?.zoomLevelMax = 20.toByte()


        mapView?.isClickable = true;
        mapView?.setBuiltInZoomControls(true);
        mapView?.mapScaleBar?.isVisible = false;

        // Centro el mapa a las coordenadas
        mapView?.model?.mapViewPosition?.center = inicialLatLong;

        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)






    }





    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)
    }

}