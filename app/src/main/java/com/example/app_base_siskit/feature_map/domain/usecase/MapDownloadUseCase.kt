package com.example.app_base_siskit.feature_map.domain.usecase

import android.app.AlertDialog
import android.app.DownloadManager
import android.content.Context
import android.content.IntentFilter
import com.example.app_base_siskit.feature_map.domain.repository.MapDownloadRepository
import javax.inject.Inject

class MapDownloadUseCase @Inject constructor(private var mapDownloadRepository: MapDownloadRepository) {

    fun  invoke(context: Context){
        mapDownloadDialog(context)
        return
    }

    /**
     * Dialogo de descarga de mapa
     * */
    private fun mapDownloadDialog(context: Context){
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Parace que aun no has descargado el mapa para uso off-line, ¿desea descargarlo ahora?")
            .setCancelable(false)
            .setPositiveButton("SI!") { dialog, id ->
                context.registerReceiver(mapDownloadRepository.onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
                mapDownloadRepository.mapDownload(context)

            }
            .setNegativeButton("Ahora NO!") { dialog, id ->
                dialog.cancel()
            }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

}