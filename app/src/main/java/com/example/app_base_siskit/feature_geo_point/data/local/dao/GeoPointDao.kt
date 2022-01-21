package com.example.app_base_siskit.feature_geo_point.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.app_base_siskit.feature_geo_point.data.local.entity.GeoPointEntity

@Dao
interface GeoPointDao {


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGeoPoint(geoPointEntity: GeoPointEntity)

    @Delete
    fun deleteGeoPoint(geoPointEntity: GeoPointEntity)

    @Update
    fun updateGeoPoint(geoPointEntity: GeoPointEntity)

    @Query("SELECT * from geo_point WHERE ID = :Id")
    suspend fun getGeoPointID(Id: Int): GeoPointEntity
}