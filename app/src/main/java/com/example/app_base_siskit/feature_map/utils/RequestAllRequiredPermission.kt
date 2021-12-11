package com.example.app_base_siskit.feature_map.utils

import android.Manifest
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Context
import android.location.LocationManager
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat

class RequestAllRequiredPermission {



    private var REQUEST_PERMISSION_LOCATION = 10
    private var REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 20
    private var REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 30
    private val REQUEST_ENABLE_GPS = 100

    /**
     * Verifica las dependencias necesarias para que la aplicacion funcione o lo fuerza a salir
     * */
    fun requestAllRequiredPermssions(context: Context) {
        Log.d(TAG,"requestAllRequiredPermssions()")

        if (!ChekPermission().hasPermission(Manifest.permission.ACCESS_FINE_LOCATION , context)){
            requestPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION, REQUEST_PERMISSION_LOCATION)
            return
        }
        if (!ChekPermission().hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE , context)){
            requestPermission(context,
                Manifest.permission.READ_EXTERNAL_STORAGE, REQUEST_PERMISSION_READ_EXTERNAL_STORAGE)
            return
        }
        if (!ChekPermission().hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE , context)){
            requestPermission(context,
                Manifest.permission.WRITE_EXTERNAL_STORAGE, REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE)
            return
        }


        Log.d(TAG,"execute -> initService()")

    }

    /**
     * Solicita Permiso mediante dialogo de sistema
     * */
    fun requestPermission(context: Context, permission: String, requestCode: Int) {
        Log.d(TAG,"requestPermission()")
        // Show the permission request
        ActivityCompat.requestPermissions(context as Activity, arrayOf(permission), requestCode)

    }



    /**
     * Chequea todos los permisos necesarios para la app
     * */
    fun hasAllRequiredPermssions(context: Context) : Boolean {

        val location = ChekPermission().hasPermission(Manifest.permission.ACCESS_FINE_LOCATION , context)
        val storageRead = ChekPermission().hasPermission(Manifest.permission.READ_EXTERNAL_STORAGE, context)
        val storageWrite = ChekPermission().hasPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE, context)


        val hasAll = location and
                storageRead and
                storageWrite

        return hasAll
    }





}