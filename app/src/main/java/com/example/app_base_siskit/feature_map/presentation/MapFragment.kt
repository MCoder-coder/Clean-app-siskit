package com.example.app_base_siskit.feature_map.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import com.example.app_base_siskit.R
import com.example.app_base_siskit.databinding.FragmentMapBinding
import com.example.app_base_siskit.feature_map.MyLocationOverlay


import org.mapsforge.core.model.LatLong
import org.mapsforge.map.android.graphics.AndroidGraphicFactory


import org.mapsforge.map.android.view.MapView
import org.mapsforge.map.model.MapViewPosition


class MapFragment : Fragment() {



    // VARIABLES GENERALES
    lateinit var mapView: MapView
    var map: org.osmdroid.views.MapView? = null
    lateinit var locationManager: LocationManager
    // Coordenada inicial para mostrar en el mapa
    var inicialLatLong = LatLong(-37.4816786, -61.9454334)
    private var _mapbinding: FragmentMapBinding? = null
    private val mapbindingget get() = _mapbinding!!
    private val mapViewModel : MapViewModel by activityViewModels()
    var isInManualAddMode: Boolean = false
    lateinit var myLocationOverlay: MyLocationOverlay
    var isDriveMode : Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _mapbinding = FragmentMapBinding.inflate(inflater , container , false)
        val view = _mapbinding!!.root

        mapConfigurationInit()

        mapViewModel.mapDownload(requireContext())
        // test
        mapViewModel.mapLoad(requireContext() , mapView)

        //floatDriveModeManualNavigation(requireContext() ,mapView )


        // TODO: renombrar variables
        val btnaddBacheClick = _mapbinding!!.addBacheClick
        val btnDriveMode = _mapbinding!!.driveMode
        val btnAddGeocontactFromGPS = _mapbinding!!.addBacheGPS
       // mapView?.getModel()?.mapViewPosition?.setCenter(inicialLatLong);
        createLocationOverlay(mapView)
        btnDriveMode.setOnClickListener {
            mapViewModel.mapDriveMode(requireActivity() , mapView , myLocationOverlay)
            changeControls(mapView)
        }

        btnaddBacheClick.setOnClickListener {
            changeManualMode(requireActivity())
        }

        btnAddGeocontactFromGPS.setOnClickListener {
            mapViewModel.mapNewGeocontactFromGps(requireActivity() , mapView, isInManualAddMode)
            Navigation.findNavController(context as Activity , R.id.nav_host_fragment ).navigate(R.id.newClientFragment)
        }

        return view

    }





    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
      //  setHasOptionsMenu(true)

    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.main, menu)
    }

 /*   override fun onOptionsItemSelected(item: MenuItem): Boolean {
     *//*   return NavigationUI.onNavDestinationSelected(
            item,
            requireView().findNavController()
        ) || super.onOptionsItemSelected(item)*//*
    }
*/
    override fun onDestroyView() {
        super.onDestroyView()
        _mapbinding = null
    }



    /**
     * Modo manual
     * (agrega marcador clickeando en el mapa)
     * */
    fun changeManualMode(context: Context){
        val addClick = _mapbinding!!.addBacheClick
        isInManualAddMode = !isInManualAddMode

        Log.i("Tag manual mode " , isInManualAddMode.toString())
        if(isInManualAddMode){
            Toast.makeText(context, "Modo Manual Activado", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(context, "Modo Manual Desactivado", Toast.LENGTH_SHORT).show()
        }
      changeControls(mapView)
    }


    /**
     * Activa y Desactiva los botones sobre el mapa
     * */
    fun changeControls( mapView: MapView) {


        val primaryColor =  ColorStateList.valueOf(Color.rgb(2 ,120,17))
        val colorGray = ColorStateList.valueOf(Color.GRAY)

        val addClick = _mapbinding!!.addBacheClick
        val addGPS = _mapbinding!!.addBacheGPS
        val btnDriveMode = _mapbinding!!.driveMode
        //mapViewModel.mapDriveMode(context , mapView)
        val driveMode = myLocationOverlay.getDriverMode()
        val manualmode = isInManualAddMode
        //mapViewModel.mapManualNavigationMode(requireActivity() , mapView , isInManualAddMode)
        Log.i("TAG" , "driveMode $driveMode")
        if (driveMode == true) {
            btnDriveMode.visibility = View.VISIBLE
            addClick.visibility = View.GONE
            addGPS.visibility = View.VISIBLE

            Log.i("TAG" , "Drive Mode")

            btnDriveMode.backgroundTintList = primaryColor

            btnDriveMode.setImageResource(R.drawable.ic_baseline_drive_eta_24)
            addGPS.setImageResource(R.drawable.ic_add_location_off_36dp)
        }else if (manualmode) {
            btnDriveMode.visibility = View.GONE
            addClick.visibility = View.VISIBLE
            addGPS.visibility = View.GONE
            Log.i("TAG" , "MANUAL MODE")

            addClick.backgroundTintList = primaryColor

            addClick.setImageResource(R.drawable.ic_drivemode_off_eta_24)
            addGPS.setImageResource(R.drawable.ic_baseline_add_location_24)
            addClick.setImageResource(R.drawable.ic_add_location_on_36dp)
        } else {
            btnDriveMode.visibility = View.VISIBLE
            addGPS.visibility = View.GONE
            addClick.visibility = View.VISIBLE
            // colors
            Log.i("TAG" , "No manual MODE")
            addClick.backgroundTintList = colorGray
            btnDriveMode.backgroundTintList =  colorGray

            btnDriveMode.setImageResource(R.drawable.ic_drivemode_off_eta_24)
            addGPS.setImageResource(R.drawable.ic_baseline_add_location_24)
            //addClick.setImageResource(R.drawable.ic_baseline_edit_location_24)

        }
    }



    private fun mapConfigurationInit(){
        locationManager = requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager

        AndroidGraphicFactory.createInstance(activity?.application);

        mapView = _mapbinding!!.map

        //load/initialize the osmdroid configuration, this can be done
        mapView.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mapView.isClickable = true;

        mapView.setBuiltInZoomControls(true);
        mapView.mapScaleBar?.isVisible = false;

        // Centro el mapa a las coordenadas
        mapView.model?.mapViewPosition?.center = inicialLatLong;

        // View Model
        mapView.setOnTouchListener(View.OnTouchListener { v, ev ->
            val actionType = ev.action
            Log.d(ContentValues.TAG, "OnTouchListener-> actionType: " + actionType)


            val tapEventResult = mapViewModel.mapNewGeocontactManualy(requireContext(), mapView, isInManualAddMode, ev)

            return@OnTouchListener tapEventResult

        })
    }


    /**
     * Location Overlay
     * (Posicion actual de GPS)
     * */
    @SuppressLint("UseCompatLoadingForDrawables")
    fun createLocationOverlay(mapView: MapView){
        // marcador para mostrar ubicacion actual
        val myLocationMarker = this.resources.getDrawable(R.drawable.ic_baseline_my_location_24)
        val bitmap = AndroidGraphicFactory.convertToBitmap(myLocationMarker)


        myLocationOverlay = MyLocationOverlay(requireActivity(), mapView.model?.mapViewPosition as MapViewPosition?,
            bitmap, null, null
        );
        mapView.layerManager?.layers?.add(myLocationOverlay);

        myLocationOverlay.isSnapToLocationEnabled = true;
        myLocationOverlay.enableMyLocation(true);
    }


}