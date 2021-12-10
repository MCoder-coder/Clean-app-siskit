package com.example.app_base_siskit.feature_map.utils

import android.app.DownloadManager
import android.content.Context
import android.content.IntentFilter
import android.util.Log
import com.example.app_base_siskit.utils.ShowDialog
import org.mapsforge.map.android.graphics.AndroidGraphicFactory
import org.mapsforge.map.android.util.AndroidUtil
import org.mapsforge.map.android.view.MapView
import org.mapsforge.map.layer.cache.TileCache
import org.mapsforge.map.layer.renderer.TileRendererLayer
import org.mapsforge.map.reader.MapFile
import org.mapsforge.map.rendertheme.InternalRenderTheme
import java.io.File

class AddOfflineMapLayer {


    // VARIABLES MAPA
    var downloadID: Long = 0
    var tileCache: TileCache? = null
    var tileRendererLayer: TileRendererLayer? = null


    fun addOfflineMap(context: Context, mapView: MapView) {
        // Cargo el archivo del mapa offline
        val myMapfile =
            File(directoryPathVersionSdk().directoryPathVersionSdk(context), "/argentina.map")

        tileCache = AndroidUtil.createTileCache(
            context, "mapcache",
            mapView.model?.displayModel?.tileSize ?: 256, 1f,
            mapView.model?.frameBufferModel?.overdrawFactor ?: 1.0
        )

        mapView.model?.mapViewPosition?.zoomLevel = 15.toByte()
        mapView.mapZoomControls?.zoomLevelMin = 14.toByte()
        mapView.mapZoomControls?.zoomLevelMax = 20.toByte()

        if (myMapfile.exists()) {
            Log.d("MAPFILE", "ARCHIVO EXISTE IUPI!")

            val MyMapDataStore = MapFile(myMapfile)
            // tile renderer layer using internal render theme
            tileRendererLayer = TileRendererLayer(
                tileCache,
                MyMapDataStore, mapView.model?.mapViewPosition, AndroidGraphicFactory.INSTANCE
            )

            // tileRendererLayer.setMapFile(File(filepath))
            val filepath = myMapfile.absolutePath

            tileRendererLayer?.setXmlRenderTheme(InternalRenderTheme.OSMARENDER)

            // Renderizo el mapa
            // only once a layer is associated with a mapView the rendering starts
            mapView.layerManager?.layers?.add(tileRendererLayer);
        } else {
            Log.d("MAPFILE", "NO EXISTE EL ARCHIVO")
            downloadDialog(context )
            //DownloadNotification(downloadID).downloadManager(context)
        }
    }


    fun downloadDialog(context: Context ){

        ShowDialog().setPositiveButton(context ,
            context.registerReceiver(BroadcastReceiverDownload.onDownloadComplete, IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE)),
            DownloadNotification(downloadID).downloadManager(context) )
    }
}