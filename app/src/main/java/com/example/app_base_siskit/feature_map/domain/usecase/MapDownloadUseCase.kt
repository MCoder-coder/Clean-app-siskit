package com.example.app_base_siskit.feature_map.domain.usecase

import android.app.Activity
import android.app.AlertDialog
import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import android.widget.Toast
import androidx.navigation.Navigation
import com.example.app_base_siskit.R
import com.example.app_base_siskit.feature_map.domain.repository.MapDownloadRepository
import javax.inject.Inject

class MapDownloadUseCase @Inject constructor(private var mapDownloadRepository: MapDownloadRepository) {

    fun  invoke(context: Context){
        mapDownloadDialog(context)
        return
    }

    var onDownloadComplete: BroadcastReceiver? = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            //Fetching the download id received with the broadcast
            val id = intent?.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1)
            Log.i("ID notification" , id.toString())
            //Checking if the received broadcast is for our enqueued download by matching download id

            // if (mapDownloadRepository.downloadID  == id) {
            Log.i("DESCARGA" , "COMPLETADA")

            //Navigation.findNavController(context as Activity, R.id.nav_host_fragment ).popBackStack()
            //Navigation.findNavController(context as Activity, R.id.nav_host_fragment ).navigate(R.id.geo_contact)
            Toast.makeText(context, "Descarga Completada", Toast.LENGTH_SHORT).show()
            //}
        }
    }

    /**
     * Dialogo de descarga de mapa
     * */
    private fun mapDownloadDialog(context: Context){
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Parace que aun no has descargado el mapa para uso off-line, ¿desea descargarlo ahora?")
            .setCancelable(false)
            .setPositiveButton("SI!") { dialog, id ->
                context.registerReceiver(onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
                mapDownloadRepository.mapDownload(context)

            }
            .setNegativeButton("Ahora NO!") { dialog, id ->
                dialog.cancel()
            }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

}