package com.example.app_base_siskit.feature_map.domain.usecase

import android.content.Context
import com.example.app_base_siskit.feature_map.domain.repository.MapDownloadRepository
import javax.inject.Inject

class MapDownloadUseCase @Inject constructor(private var mapDownloadRepository: MapDownloadRepository) {

    fun  invoke(context: Context){
        return mapDownloadRepository.mapDownload(context)
    }
}