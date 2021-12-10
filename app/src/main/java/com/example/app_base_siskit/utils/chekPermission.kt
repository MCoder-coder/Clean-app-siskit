package com.example.app_base_siskit.utils

import android.Manifest
import android.content.ContentValues.TAG
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import androidx.core.content.ContextCompat

class chekPermission {


    fun checkPermissionForExternalStorage(context: Context): Boolean {
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
                hasPermission = ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED
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
}