package com.example.app_base_siskit.feature_map.presentation

import android.Manifest
import android.Manifest.permission.READ_EXTERNAL_STORAGE
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.DOWNLOAD_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.util.Log
import android.view.*
import android.widget.ImageButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.widget.Toolbar
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.example.app_base_siskit.BuildConfig
import com.example.app_base_siskit.MainActivity
import com.example.app_base_siskit.R
import com.example.app_base_siskit.utils.Constants
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
import org.mapsforge.map.reader.MapFile
import org.mapsforge.map.rendertheme.InternalRenderTheme
import org.osmdroid.views.overlay.mylocation.GpsMyLocationProvider
import org.osmdroid.views.overlay.mylocation.MyLocationNewOverlay
import java.io.File


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

    // Variables para descarga de archivo de mapa
    private var downloadID: Long = 0
    private var onDownloadComplete: BroadcastReceiver? = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            //Fetching the download id received with the broadcast
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            //Checking if the received broadcast is for our enqueued download by matching download id
            if (downloadID == id) {
                Toast.makeText(context, "Download Completed", Toast.LENGTH_SHORT).show()
            }
        }
    }

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


        // Chequeo Permisos de acceso a External Storage
        if(checkPermissionForExternalStorage(requireActivity().application)){
            // Agrego el mapa off-line
            addOfflineMapLayer()
        }


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

    /**
     * Chequea los permisos para ExternalStorage
     * */
    fun checkPermissionForExternalStorage(context: Context): Boolean {
       /* if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                // Show the permission request
                ActivityCompat.requestPermissions(requireActivity(), arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    REQUEST_PERMISSION_EXTERNALSTORAGE)
                false
            }
        } else {
            true
        }
        return*/
        var hasPermission = false
        hasPermission =
                // WRITE_EXTERNAL_STORAGE/READ_EXTERNAL_STORAGE: Android 10 - Q (API 29) o Mayor
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                Log.d(TAG,"Android 10 - Q (API 29) o mayor, permiso por defecto: true")
                true
            }
            // WRITE_EXTERNAL_STORAGE/READ_EXTERNAL_STORAGE: Android 6 - M (API 23) o mayor
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.d(TAG,"Android 6 - M (API 23) o mayor, hay que pedir permisos por cuadro de dialogo")
                hasPermission = ContextCompat.checkSelfPermission(context, READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                // checkPermission(this, WRITE_EXTERNAL_STORAGE, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE)
                return hasPermission
            }
            // WRITE_EXTERNAL_STORAGE/READ_EXTERNAL_STORAGE: Android menor a 6 - M (API 23)
            else{
                Log.d(TAG,"Android menor a 6 - M (API 23), los permisos se aceptan al instalar la app: true")
                true
            }

        return hasPermission
    }

    /**
     *  Agrega el mapa offline a las capas del mapa
     * */
    fun addOfflineMapLayer(){
        // Cargo el archivo del mapa offline

        val DownloadPath = if(Build.VERSION.SDK_INT>Build.VERSION_CODES.Q) {
            Log.d("MAPFILE", "Android ver >= Q")
            Log.d("MAPFILE", "usa getExternalFilesDir()")
            activity?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        }else{
            Log.d("MAPFILE", "Android ver < Q")
            Log.d("MAPFILE", "usa Environment.getExternalStoragePublicDirectory")
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
            //activity?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        }
//        val DownloadPath = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
//        val DownloadPath_ex = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);


        val MyMapfile = File(DownloadPath, "/argentina.map")

        if (MyMapfile.exists() == true) {
            Log.d("MAPFILE", "ARCHIVO EXISTE IUPI!")

            val MyMapDataStore = MapFile( MyMapfile )
            // tile renderer layer using internal render theme
            tileRendererLayer = TileRendererLayer(tileCache,
                MyMapDataStore, mapView?.getModel()?.mapViewPosition, AndroidGraphicFactory.INSTANCE)

            // tileRendererLayer.setMapFile(File(filepath))
            val filepath = MyMapfile.absolutePath

            tileRendererLayer?.setXmlRenderTheme(InternalRenderTheme.OSMARENDER)

            // Renderizo el mapa
            // only once a layer is associated with a mapView the rendering starts
            mapView?.getLayerManager()?.getLayers()?.add(tileRendererLayer);
        } else {
            Log.d("MAPFILE", "NO EXISTE EL ARCHIVO")
            downloadDialog()
        }
    }


    /**
     * Download File with notificaction
     * */
    private fun beginDownload(){
        Log.d("MAPFILE", "beginDownload()")

        val DownloadPath = if(Build.VERSION.SDK_INT>Build.VERSION_CODES.Q) {
            Log.d("MAPFILE", "Android ver >= Q")
            Log.d("MAPFILE", "usa getExternalFilesDir()")
            requireActivity().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        }else{
            Log.d("MAPFILE", "Android ver < Q")
            Log.d("MAPFILE", "usa Environment.getExternalStoragePublicDirectory")
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//            activity?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        }
        val file = File(DownloadPath,"argentina.map")
        /*
        Create a DownloadManager.Request with all the information necessary to start the download
         */
        val request: DownloadManager.Request = DownloadManager.Request(Uri.parse(Constants.DOWNLOAD_MAP_URI ))
            .setTitle("INNOVArro")// Title of the Download Notification
            .setDescription("Descargando Mapa")// Description of the Download Notification
            .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
            .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
            //.setRequiresCharging(false)// Set if charging is required to begin the download
            .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
            .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
        val downloadManager: DownloadManager = requireActivity().getSystemService(DOWNLOAD_SERVICE) as DownloadManager;
        downloadID = downloadManager.enqueue(request);// enqueue puts the download request in the queue.
    }


    /**
     * Dialogo de descarga de mapa
     * */
    fun downloadDialog(){
        val builder = AlertDialog.Builder(requireActivity())
        builder.setMessage("Parace que aun no has descargado el mapa para uso off-line, ¿desea descargarlo ahora?")
            .setCancelable(false)
            .setPositiveButton("SI!") { dialog, id ->
                activity?.registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
                beginDownload()
            }
            .setNegativeButton("Ahora NO!") { dialog, id ->
                dialog.cancel()
            }
        val alert: AlertDialog = builder.create()
        alert.show()
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