package com.example.app_base_siskit.feature_geo_contact.di

import android.app.Application
import androidx.room.Room
import com.example.app_base_siskit.feature_geo_contact.data.local.GeoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeoModule {

    @Provides
    @Singleton
    fun provideGeoDatabase(app: Application) : GeoDatabase{
        return Room.databaseBuilder(
            app ,
            GeoDatabase::class.java, "geo_db"
        ).build()
    }
}