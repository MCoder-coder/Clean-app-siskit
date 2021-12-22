package com.example.app_base_siskit.feature_map.domain.repository

import android.app.DownloadManager
import android.content.Context

interface MapDownloadRepository {
    fun mapDownload(context: Context , downloadID : Long)
}