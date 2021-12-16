package com.example.app_base_siskit.feature_map.presentation

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.createViewModelLazy
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.app_base_siskit.R
import com.example.app_base_siskit.databinding.FragmentMapBinding
import com.example.app_base_siskit.feature_map.MyLocationOverlay
import com.example.app_base_siskit.feature_map.utils.CreateLocationOverlay


import com.example.app_base_siskit.utils.PermissionHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton
import org.mapsforge.core.model.LatLong
import org.mapsforge.map.android.graphics.AndroidGraphicFactory


import org.mapsforge.map.android.view.MapView
import org.mapsforge.map.layer.cache.TileCache
import org.mapsforge.map.layer.overlay.Marker
import org.mapsforge.map.layer.renderer.TileRendererLayer
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate the layout for this fragment
        _mapbinding = FragmentMapBinding.inflate(inflater , container , false)
        val view = _mapbinding!!.root

        mapConfigurationInit()
        PermissionHelper().showRequiredPermissionsDialog(requireActivity())
        mapViewModel.mapDownload(requireContext())
        mapViewModel.mapLoad(requireContext() , mapView)

        floatDriveModeManualNavigation(requireContext() ,mapView )

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

    override fun onDestroyView() {
        super.onDestroyView()
        _mapbinding = null
    }



    private fun floatDriveModeManualNavigation(context: Context, mapView: MapView){
        val btnModeDrive = _mapbinding!!.driveMode

        btnModeDrive.setOnClickListener {


            if (btnModeDrive.isClickable) {

                mapViewModel.mapDriveMode(requireContext(), mapView)
                btnModeDrive.backgroundTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.colorAccent))
            } else {

                btnModeDrive.backgroundTintList =
                    ColorStateList.valueOf(resources.getColor(R.color.gray))

            }


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
    }


}