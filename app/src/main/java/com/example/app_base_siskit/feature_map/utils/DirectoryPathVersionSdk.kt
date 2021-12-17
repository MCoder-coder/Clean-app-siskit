package com.example.app_base_siskit.feature_map.utils

import android.content.Context
import android.os.Build
import android.os.Environment
import android.util.Log
import java.io.File

class DirectoryPathVersionSdk {


     fun directoryPathVersionSdk(context: Context): File? {

         // TODO: verificar si esto funciona bien en android api 31,30,29,28 y 23

         // Android 11 (solo ScopedStorage)
         val filepath = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            Log.d("MAPFILE", "Android 11 (R), 12 (S), o mayor ")
            Log.d("MAPFILE", "usa getExternalFilesDir()")
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        } else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.Q){
            // Android 10 (Ambos Storages con flag en manifest android:requestLegacyExternalStorage="true")
            Log.d("MAPFILE", "Android 10 (Q)")
            Log.d("MAPFILE", "usa Environment.getExternalStoragePublicDirectory")
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        } else {
            Log.d("TAG", "Android 9 (P) o menor - getExternalStoragePublicDirectory() " );
            // Android 9 o menor (No existe ScopedStorage) usa getExternalStoragePublicDirectory()
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        }

        return filepath
    }

    fun getFile(context: Context) : File{
        return  File(DirectoryPathVersionSdk().directoryPathVersionSdk(context), "/argentina.map")
    }

}