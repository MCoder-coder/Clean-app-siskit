package com.example.app_base_siskit.utils

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.location.LocationManager
import android.os.Build
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionHelper {




    var permissionsAlertDialog: AlertDialog? = null
    private lateinit var locationManager : LocationManager
    private var REQUEST_PERMISSION_LOCATION = 10
    private var REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 20
    private var REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 30
    private val REQUEST_ENABLE_GPS = 100

    /**
     * Checkea un permiso
     * */
    fun hasPermission(permission:String, context: Context ):Boolean {
        Log.d(TAG, "hasPermission($permission)")
        var hasPermission = false;

        // Caso especial de permiso de almacenamiento entre apis 28/29/30/31
        if(permission == Manifest.permission.WRITE_EXTERNAL_STORAGE || permission == Manifest.permission.READ_EXTERNAL_STORAGE) {

            hasPermission =
                    // WRITE_EXTERNAL_STORAGE/READ_EXTERNAL_STORAGE: Android 10 - Q (API 29) o Mayor
                if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
                    Log.d(TAG,"Android 10 - Q (API 29) o mayor, permiso por defecto: true")
                    true
                }
                // WRITE_EXTERNAL_STORAGE/READ_EXTERNAL_STORAGE: Android 6 - M (API 23) o mayor
                else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    Log.d(TAG,"Android 6 - M (API 23) o mayor, hay que pedir permisos por cuadro de dialogo")
                    hasPermission = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
                    // checkPermission(this, WRITE_EXTERNAL_STORAGE, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE)
                    return hasPermission
                }
                // WRITE_EXTERNAL_STORAGE/READ_EXTERNAL_STORAGE: Android menor a 6 - M (API 23)
                else{
                    Log.d(TAG,"Android menor a 6 - M (API 23), los permisos se aceptan al instalar la app: true")
                    true
                }

            Log.d(TAG, "$permission: $hasPermission")
            return hasPermission
        }

        hasPermission = ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED
        Log.d(TAG, "$permission: $hasPermission")

        return hasPermission
    }

    /**
     * Verifica las dependencias necesarias para que la aplicacion funcione o lo fuerza a salir
     * */
    fun requestAllRequiredPermssions(context: Context) {
        Log.d(ContentValues.TAG,"requestAllRequiredPermssions()")

        if (!hasPermission(Manifest.permission.ACCESS_FINE_LOCATION , context)){
            requestPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_PERMISSION_LOCATION)
            return
        }
        if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE , context)){
            requestPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_PERMISSION_READ_EXTERNAL_STORAGE)
            return
        }
        if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE , context)){
            requestPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE)
            return
        }
        if(!isGpsEnabled()){
            showRequiredGpsEnabledDialog(context)
            return
        }


        Log.d(ContentValues.TAG,"execute -> initService()")

    }

    /**
     * Solicita Permiso mediante dialogo de sistema
     * */
    fun requestPermission(context: Context, permission: String, requestCode: Int) {
        Log.d(ContentValues.TAG,"requestPermission()")
        // Show the permission request
        ActivityCompat.requestPermissions(context as Activity, arrayOf(permission), requestCode)

    }



    /**
     * Chequea todos los permisos necesarios para la app
     * */
    fun hasAllRequiredPermssions(context: Context) : Boolean {

        val location = hasPermission(Manifest.permission.ACCESS_FINE_LOCATION , context)
        val storageRead = hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE, context)
        val storageWrite = hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, context)
        //val gpsEnabled = isGpsEnabled()

        val hasAll = location and
                storageRead and
                storageWrite

        return hasAll
    }


    private fun isGpsEnabled() : Boolean {
        Log.d(TAG,"isGpsEnabled()")
       // var locationManager: LocationManager
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            // GPS ACTIVADO
            return true;
        }
        return false
    }


    /**
     * Verifica los permisos necesarios para la aplicacion
     * Si falta alguno muestra un mensaje de aviso
     * Si acepta
     *      Solicita los permisos
     * Si no
     *      Sale de la app
     * */
    fun showRequiredPermissionsDialog(context: Context){
        Log.d(TAG,"showRequiredPermissionsDialog()")

        val hasAllRequiredPermssions = hasAllRequiredPermssions(context)

        if(!hasAllRequiredPermssions) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Para utilizar la app es necesario que acepte los permisos de: Ubicacion, Lectura y Escritura; y mantener el GPS encendido.")
                .setCancelable(false)
                .setPositiveButton("OK, Entendido!") { dialog, id ->
                    requestAllRequiredPermssions(context)
                }
                .setNegativeButton("Cerrar App") { dialog, id ->
                    dialog.cancel()
                }
            permissionsAlertDialog = builder.create()
            permissionsAlertDialog!!.show()
            permissionsAlertDialog!!.setOnCancelListener {
                it.dismiss()
               // finish()
            }
        }else{
           // initService()
        }
    }


    private fun showRequiredGpsEnabledDialog(context: Context) {
        Log.d(TAG,"showRequiredGpsEnabledDialog()")

        val builder = AlertDialog.Builder(context)
        builder.setMessage("Parece que su GPS esta desactivado, es necesario que lo active.")
            .setCancelable(false)
            .setPositiveButton("Activar") { dialog, id ->

            }
            .setNegativeButton("Cancelar") { dialog, id ->
                Toast.makeText(
                    context,
                    "Debe activar el GPS para usar la App",
                    Toast.LENGTH_LONG
                ).show()
                dialog.cancel()

            }
        val alert: AlertDialog = builder.create()
        alert.show()

    }


}