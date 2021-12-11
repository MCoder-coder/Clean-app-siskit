package com.example.app_base_siskit.feature_map.utils

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.app_base_siskit.utils.ShowDialog
import org.mapsforge.map.android.view.MapView

class ChekPermission {

     val REQUEST_PERMISSION_LOCATION = 10


    fun hasPermission(permission: String,context: Context): Boolean {
        var hasPermission = false
        hasPermission =
                // WRITE_EXTERNAL_STORAGE/READ_EXTERNAL_STORAGE: Android 10 - Q (API 29) o Mayor
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                Log.d(TAG,"Android 10 - Q (API 29) o mayor, permiso por defecto: true")

                // checkPermission(this, WRITE_EXTERNAL_STORAGE, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE)
                return hasPermission

            }
            // WRITE_EXTERNAL_STORAGE/READ_EXTERNAL_STORAGE: Android 6 - M (API 23) o mayor
            else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                Log.d(TAG,"Android 6 - M (API 23) o mayor, hay que pedir permisos por cuadro de dialogo")

                // checkPermission(this, WRITE_EXTERNAL_STORAGE, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE)
                hasPermission = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
                return hasPermission
            }
            // WRITE_EXTERNAL_STORAGE/READ_EXTERNAL_STORAGE: Android menor a 6 - M (API 23)
            else{
                Log.d(TAG,"Android menor a 6 - M (API 23), los permisos se aceptan al instalar la app: true")
                true
            }

        hasPermission = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        Log.d(TAG, "$permission: $hasPermission")

        return hasPermission
    }

    /**
     * Chequea los permisos para ExternalStorage
     * */
    fun checkPermissionForExternalStorage(context: Context): Boolean {

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (context.checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                PackageManager.PERMISSION_GRANTED) {
                true
            } else {

                ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    20)
                false
            }
        } else {
            true
        }
    }


    /**
     * Chequea los permisos para ubicacion
     * */
    fun checkPermissionForLocation(context: Context ): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            if (context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
                true
            } else {
                // Show the permission request
                ActivityCompat.requestPermissions(context as Activity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSION_LOCATION)
                false
            }
        } else {
            true
        }
    }


    fun checkPermissionFragment(context: Context , mapView : MapView){

        // Chequeo Permisos de acceso a External Storage
        if(ChekPermission().checkPermissionForExternalStorage(context)){
           // AddOfflineMapLayer().addOfflineMap(context, mapView)
        }

        ShowDialog().showRequiredPermissionsDialog(context)
        if (ChekPermission().checkPermissionForLocation(context)){

            CreateLocationOverlay().createLocationOverlay(context, mapView)
        }
    }
}