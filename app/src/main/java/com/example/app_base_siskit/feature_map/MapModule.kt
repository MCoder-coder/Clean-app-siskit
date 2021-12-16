package com.example.app_base_siskit.feature_map

import com.example.app_base_siskit.feature_map.data.repository.MapDownloadRepositoryImpl
import com.example.app_base_siskit.feature_map.data.repository.MapDriveModeRepositoryImpl
import com.example.app_base_siskit.feature_map.data.repository.MapManualNavigationRepositoryImpl
import com.example.app_base_siskit.feature_map.data.repository.MapLoadRepositoryImpl
import com.example.app_base_siskit.feature_map.domain.repository.MapDownloadRepository
import com.example.app_base_siskit.feature_map.domain.repository.MapDriveModeRepository
import com.example.app_base_siskit.feature_map.domain.repository.MapManualNavigationModeRepository
import com.example.app_base_siskit.feature_map.domain.repository.MapLoadRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module()
@InstallIn(SingletonComponent::class)
class MapModule {

    @Singleton
    @Provides
    fun provideMapLoadLocalRepository() : MapLoadRepository{
        return MapLoadRepositoryImpl()
    }


    @Singleton
    @Provides
    fun provideMapDownloadRepository() : MapDownloadRepository{
        return  MapDownloadRepositoryImpl()
    }

    @Singleton
    @Provides
    fun provideMapManualNavigationModeRepository() : MapManualNavigationModeRepository{
        return  MapManualNavigationRepositoryImpl()
    }

    @Singleton
    @Provides
    fun provideMapDriveModelRepository(): MapDriveModeRepository{
        return  MapDriveModeRepositoryImpl()
    }




}