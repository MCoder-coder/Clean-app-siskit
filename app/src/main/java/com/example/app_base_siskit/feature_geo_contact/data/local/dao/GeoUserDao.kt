package com.example.app_base_siskit.feature_geo_contact.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.app_base_siskit.feature_geo_contact.data.local.entity.GeoUserEntity

@Dao
interface GeoUserDao {
    @Query("SELECT * FROM geo_user")
    fun getAllUser() : List<GeoUserEntity>

    @Query("SELECT * FROM geo_user  ORDER BY  ID DESC")
    fun getAllGeoUserOrderByDesc() : LiveData<List<GeoUserEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGeoUser(geoUserEntity: GeoUserEntity)

    @Delete
    fun deleteGeoUser(geoUserEntity: GeoUserEntity)

    @Update
    fun updateGeoUser(geoUserEntity: GeoUserEntity)

    @Query("SELECT * from geo_user WHERE ID = :Id")
    suspend fun getGeoUserID(Id: Int): GeoUserEntity
}