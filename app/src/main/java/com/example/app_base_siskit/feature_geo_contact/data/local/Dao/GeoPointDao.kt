package com.example.app_base_siskit.feature_geo_contact.data.local.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.app_base_siskit.feature_geo_contact.data.local.entity.GeoContactEntity
import com.example.app_base_siskit.feature_geo_contact.data.local.entity.GeoPointEntity

@Dao
interface GeoPointDao {
    @Query("SELECT * FROM geo_point")
    fun getAllPoint() : List<GeoPointEntity>

    @Query("SELECT * FROM geo_point  ORDER BY  ID DESC")
    fun getAllGeoPointOrderByDesc() : LiveData<List<GeoPointEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGeoPoint(geoPointEntity: GeoPointEntity)

    @Delete
    fun deleteGeoPoint(geoPointEntity: GeoPointEntity)

    @Update
    fun updateGeoPoint(geoPointEntity: GeoPointEntity)

    @Query("SELECT * from geo_point WHERE ID = :Id")
    suspend fun getGeoPointID(Id: Int): GeoPointEntity
}