package com.example.app_base_siskit.utils

import android.Manifest
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.app.ActivityCompat.requestPermissions
import androidx.core.app.ActivityCompat.startActivityForResult
import androidx.core.content.ContextCompat

class PermissionHelper(context: AppCompatActivity) {


    val TAG : String = "TAG"

    var permissionsAlertDialog: AlertDialog? = null
    private lateinit var locationManager : LocationManager
    private var REQUEST_PERMISSION_LOCATION = 10
    private var REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 20
    private var REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 30
    private val REQUEST_ENABLE_GPS = 100
    var activityContext : AppCompatActivity = context;

    init {
        locationManager = activityContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    /**
     * Checkea un permiso
     * */
    private fun hasPermission(permission:String) : Boolean {
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
                    hasPermission = ContextCompat.checkSelfPermission(activityContext, permission) == PackageManager.PERMISSION_GRANTED
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

        hasPermission = ContextCompat.checkSelfPermission(activityContext, permission) == PackageManager.PERMISSION_GRANTED
        Log.d(TAG, "$permission: $hasPermission")

        return hasPermission
    }

    /**
     * Verifica las dependencias necesarias para que la aplicacion funcione o lo fuerza a salir
     * */
    fun requestAllRequiredPermssions() {
        Log.d(ContentValues.TAG,"requestAllRequiredPermssions()")

        if (!hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)){
            requestPermissionHandler(Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_PERMISSION_LOCATION)
            return
        }
        if (!hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)){
            requestPermissionHandler(Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_PERMISSION_READ_EXTERNAL_STORAGE)
            return
        }
        if (!hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            requestPermissionHandler(Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE)
            return
        }
        if(!isGpsEnabled()){
            showRequiredGpsEnabledDialog()
            return
        }


        Log.d(ContentValues.TAG,"execute -> initService()")
        // initService()
    }

    /**
     * Solicita Permiso mediante dialogo de sistema
     * */
    private fun requestPermissionHandler(permission: String, requestCode: Int) {
        Log.d(ContentValues.TAG,"requestPermission()")
        // Show the permission request
        requestPermissions(activityContext as Activity, arrayOf(permission), requestCode)
    }



    /**
     * Chequea todos los permisos necesarios para la app
     * */
    private fun hasAllRequiredPermssions() : Boolean {

        val location = hasPermission(Manifest.permission.ACCESS_FINE_LOCATION)
        val storageRead = hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
        val storageWrite = hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
        val gpsEnabled = isGpsEnabled()

        val hasAll = location and
                storageRead and
                storageWrite and
                gpsEnabled

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
     * Muestra Dialogo para permisos necesarios
     *
     * Si falta algun permiso muestra un mensaje de aviso
     * Si acepta
     *      Solicita los permisos
     * Si no
     *      Sale de la app
     * */
    fun showRequiredPermissionsDialog(resolve: () -> Unit, reject: () ->Unit ){

        Log.d(TAG,"showRequiredPermissionsDialog()")

        val hasAllRequiredPermssions = hasAllRequiredPermssions()
        Log.d(TAG,"hasAllRequiredPermssions " + hasAllRequiredPermssions)

        if(!hasAllRequiredPermssions) {
            val builder = AlertDialog.Builder(activityContext)
            builder.setMessage("Para utilizar la app es necesario que acepte los permisos de: Ubicacion, Lectura y Escritura; y mantener el GPS encendido.")
                .setCancelable(false)
                .setPositiveButton("OK, Entendido!") { dialog, id ->

                    resolve();

                }
                .setNegativeButton("Cerrar App") { dialog, id ->
                    dialog.cancel()
                }
            permissionsAlertDialog = builder.create()
            permissionsAlertDialog!!.show()
            permissionsAlertDialog!!.setOnCancelListener {
                it.dismiss()
                reject()
            }
        }else{
            // TODO Iniciar captura de coordenadas
           // initService()
        }
    }


    private fun showRequiredGpsEnabledDialog() {
        Log.d(TAG,"showRequiredGpsEnabledDialog()")

        val builder = AlertDialog.Builder(activityContext)
        builder.setMessage("Parece que su GPS esta desactivado, es necesario que lo active.")
            .setCancelable(false)
            .setPositiveButton("Activar") { dialog, id ->
                // Abro la config del telefono de GPS para que lo active
                startActivityForResult(activityContext, Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), REQUEST_ENABLE_GPS, null)
            }
            .setNegativeButton("Cancelar") { dialog, id ->
                Toast.makeText(
                    activityContext,
                    "Debe activar el GPS para usar la App",
                    Toast.LENGTH_LONG
                ).show()
                dialog.cancel()
                activityContext.finish()
            }
        val alert: AlertDialog = builder.create()
        alert.show()

    }



    /**
     * Luego de chequear permisos
     * */
    fun onRequestPermissionsResultHandler(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        Log.d(TAG,"PermissionHelper@onRequestPermissionsResultHandler()")
        if(grantResults.size>0) {

//            if (requestCode == REQUEST_ENABLE_GPS) {
//                checkGpsStatus()
//            }

            val hasPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED
            if (!hasPermission) {

                if(isUserCheckNeverAskAgain(permissions[0])){

                    openSystemSettings()

                    Toast.makeText(activityContext, "Permiso Denegado definitivamente, active el permiso en configuracion", Toast.LENGTH_LONG).show()
                    return

                }else {

                    Toast.makeText(
                        activityContext,
                        "Permiso Denegado, saliendo de la aplicacion",
                        Toast.LENGTH_LONG
                    ).show()

                    activityContext.finish()
                }
            }
        }
    }

    /**
     * Checkea si el usuario denego el permiso para siempre (con no volver a mostrar),
     * Si asi fuese hay que mostrarle un mensaje y enviarlo a Settings para que los acepte desde la configuracion de la app
     * */
    private fun isUserCheckNeverAskAgain(permission: String) =
        !ActivityCompat.shouldShowRequestPermissionRationale(
            activityContext as Activity,
            permission
        )

    /**
     * Abre la pantalla de "settings" de la aplicacion
     * */
    private fun openSystemSettings(){
        val intent = Intent()
        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", activityContext.packageName, null)
        intent.setData(uri)
        startActivityForResult(activityContext, intent, 200, null)
    }


}