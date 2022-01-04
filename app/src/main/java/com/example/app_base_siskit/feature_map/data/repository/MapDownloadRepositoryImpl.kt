package com.example.app_base_siskit.feature_map.data.repository

import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.app_base_siskit.R
import com.example.app_base_siskit.feature_map.data.remote.MapApiDownload
import com.example.app_base_siskit.feature_map.domain.repository.MapDownloadRepository
import com.example.app_base_siskit.feature_map.utils.DirectoryPathVersionSdk
import com.example.app_base_siskit.utils.Constants
import java.io.File

class MapDownloadRepositoryImpl : MapDownloadRepository {
    override var downloadID: Long = 0

    override fun mapDownload(context: Context) {

        Log.d("MAPFILE", "beginDownload()")

        val file = File(DirectoryPathVersionSdk().directoryPathVersionSdk(context), "argentina.map")
        // Create a DownloadManager.Request with all the information necessary to start the download
        val request: DownloadManager.Request =
            DownloadManager.Request(Uri.parse(MapApiDownload.DOWNLOAD_MAP_URI))
                .setTitle("INNOVArro")// Title of the Download Notification
                .setDescription("Descargando Mapa")// Description of the Download Notification
                .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)// Visibility of the download Notification
                .setDestinationUri(Uri.fromFile(file))// Uri of the destination file
                //.setRequiresCharging(false)// Set if charging is required to begin the download
                .setAllowedOverMetered(true)// Set if download is allowed on Mobile network
                .setAllowedOverRoaming(true);// Set if download is allowed on roaming network
        val downloadManager: DownloadManager =
            context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager;
        downloadID =
            downloadManager.enqueue(request);// enqueue puts the download request in the queue.

    }

}




