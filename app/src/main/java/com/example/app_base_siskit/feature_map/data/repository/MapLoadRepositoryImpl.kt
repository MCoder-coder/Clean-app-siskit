package com.example.app_base_siskit.feature_map.data.repository

import android.content.Context
import android.util.Log
import com.example.app_base_siskit.feature_map.domain.repository.MapLoadRepository
import com.example.app_base_siskit.feature_map.utils.DirectoryPathVersionSdk
import com.example.app_base_siskit.feature_map.utils.MapDownloadNotification
import org.mapsforge.map.android.graphics.AndroidGraphicFactory
import org.mapsforge.map.android.util.AndroidUtil
import org.mapsforge.map.android.view.MapView
import org.mapsforge.map.layer.cache.TileCache
import org.mapsforge.map.layer.renderer.TileRendererLayer
import org.mapsforge.map.reader.MapFile
import org.mapsforge.map.rendertheme.InternalRenderTheme
import java.io.File

class MapLoadRepositoryImpl : MapLoadRepository{


    // VARIABLES MAPA
    var tileCache: TileCache? = null
    var tileRendererLayer: TileRendererLayer? = null

    override fun mapLoadLocalFile(context: Context, mapView: MapView) {
        // Cargo el archivo del mapa offline
        val myMapfile = DirectoryPathVersionSdk().getFile(context)

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
            tileRendererLayer?.setXmlRenderTheme(InternalRenderTheme.OSMARENDER)
            // Renderizo el mapa
            // only once a layer is associated with a mapView the rendering starts
            mapView.layerManager?.layers?.add(tileRendererLayer);
        }
    }


}