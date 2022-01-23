package com.example.app_base_siskit.feature_geo_contact

import android.content.Context
import com.example.app_base_siskit.common.GeoDatabase
import com.example.app_base_siskit.di.module.NetworkModule
import com.example.app_base_siskit.feature_geo_contact.data.remote.GeoUserAPi
import com.example.app_base_siskit.feature_geo_contact.data.repository.GeoUserRepositoryImpl
import com.example.app_base_siskit.feature_geo_contact.domain.repository.GeoUserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton


@Module(includes = [NetworkModule::class])
@InstallIn(SingletonComponent::class)
class GeoModule {

    @Singleton
    @Provides
    fun provideGeoUserApi(retrofit: Retrofit) : GeoUserAPi {
        return retrofit.create(GeoUserAPi::class.java)
    }



    @Singleton
    @Provides
    fun provideGeoUserRepository(geoUserAPi: GeoUserAPi , @ApplicationContext context: Context) : GeoUserRepository {
        return GeoUserRepositoryImpl(geoUserAPi , context)
    }


}