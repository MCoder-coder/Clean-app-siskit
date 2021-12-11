package com.example.app_base_siskit.feature_map.utils

import android.content.Context
import android.os.Build
import android.os.Environment
import android.util.Log
import java.io.File

class DirectoryPathVersionSdk {

     fun directoryPathVersionSdk(context: Context): File? {

        val filepath = if (Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            Log.d("MAPFILE", "Android ver >= Q")
            Log.d("MAPFILE", "usa getExternalFilesDir()")
            context.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        } else {
            Log.d("MAPFILE", "Android ver < Q")
            Log.d("MAPFILE", "usa Environment.getExternalStoragePublicDirectory")
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
//            activity?.getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS)
        }

        return filepath
    }

}