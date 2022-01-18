package com.example.app_base_siskit.di.module

import android.app.Application
import androidx.room.Room
import com.example.app_base_siskit.common.GeoDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeoDatabaseModule {
    @Provides
    @Singleton
    fun provideGeoDatabase(app: Application) : GeoDatabase {
        return Room.databaseBuilder(
            app ,
            GeoDatabase::class.java, "geo_db"
        ).build()
    }

    @Singleton
    @Provides
    fun provideGeoDao(
        database: GeoDatabase
    ) = database
}