package com.example.app_base_siskit.feature_map.utils

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.widget.Toast

class MapDownloadNotification() {
    var downloadID: Long = 0
    var onDownloadComplete: BroadcastReceiver? = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            //Fetching the download id received with the broadcast
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            Log.i("ID notification" , id.toString())
            //Checking if the received broadcast is for our enqueued download by matching download id

            if (downloadID == id) {
                Log.i("DESCARGA" , "COMPLETADA")
                Toast.makeText(context, "Descarga Completada", Toast.LENGTH_SHORT).show()
            }
        }
    }

    /**
     * Dialogo de descarga de mapa
     * */
    fun downloadDialog(context: Context , mapDownload : Unit){
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Parace que aun no has descargado el mapa para uso off-line, ¿desea descargarlo ahora?")
            .setCancelable(false)
            .setPositiveButton("SI!") { dialog, id ->
                context.registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
                mapDownload

            }
            .setNegativeButton("Ahora NO!") { dialog, id ->
                dialog.cancel()
            }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

}