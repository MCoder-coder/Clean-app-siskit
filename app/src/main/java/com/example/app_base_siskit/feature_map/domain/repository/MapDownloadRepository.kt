package com.example.app_base_siskit.feature_map.domain.repository

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context

interface MapDownloadRepository {

    var downloadID : Long

    fun mapDownload(context: Context)

}