package com.example.app_base_siskit.feature_geo_contact.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.app_base_siskit.feature_geo_contact.data.local.Dao.GeoContactDao
import com.example.app_base_siskit.feature_geo_contact.data.local.Dao.GeoPointDao
import com.example.app_base_siskit.feature_geo_contact.data.local.Dao.GeoUserDao
import com.example.app_base_siskit.feature_geo_contact.data.local.entity.GeoContactEntity
import com.example.app_base_siskit.feature_geo_contact.data.local.entity.GeoPointEntity
import com.example.app_base_siskit.feature_geo_contact.data.local.entity.GeoUserEntity

@Database(
    entities = [GeoContactEntity::class , GeoPointEntity::class , GeoUserEntity::class],
    version = 1
)
abstract class GeoDatabase : RoomDatabase() {
    abstract val geoContactDao: GeoContactDao
    abstract val geoPointDao: GeoPointDao
    abstract val getUserDao : GeoUserDao
}