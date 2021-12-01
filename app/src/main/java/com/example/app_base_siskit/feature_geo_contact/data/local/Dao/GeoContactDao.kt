package com.example.app_base_siskit.feature_geo_contact.data.local.Dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.app_base_siskit.feature_geo_contact.data.local.entity.GeoContactEntity

@Dao
interface GeoContactDao {
    @Query("SELECT * FROM geo_contact")
    fun getAllContact() : List<GeoContactEntity>

    @Query("SELECT * FROM geo_contact  ORDER BY  ID DESC")
    fun getAllGeoContactOrderByDesc() : LiveData<List<GeoContactEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertGeoContact(geoContactEntity: GeoContactEntity)

    @Delete
    fun deleteGeoContact(geoContactEntity: GeoContactEntity)

    @Update
    fun updateGeoContact(geoContactEntity: GeoContactEntity)

    @Query("SELECT * from geo_contact WHERE ID = :Id")
    suspend fun getGeoContactID(Id: Int):GeoContactEntity

}