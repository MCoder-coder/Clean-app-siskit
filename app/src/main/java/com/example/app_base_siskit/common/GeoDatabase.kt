package com.example.app_base_siskit.common

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.app_base_siskit.feature_geo_contact.data.local.dao.GeoContactDao
import com.example.app_base_siskit.feature_geo_point.data.local.dao.GeoPointDao
import com.example.app_base_siskit.feature_geo_contact.data.local.dao.GeoUserDao
import com.example.app_base_siskit.feature_geo_contact.data.local.entity.GeoContactEntity
import com.example.app_base_siskit.feature_geo_point.data.local.entity.GeoPointEntity
import com.example.app_base_siskit.feature_geo_contact.data.local.entity.GeoUserEntity
import kotlinx.coroutines.CoroutineScope

@Database(
    entities = [GeoContactEntity::class , GeoPointEntity::class , GeoUserEntity::class],
    version = 1
)
abstract class GeoDatabase : RoomDatabase() {
    abstract fun geoContactDao(): GeoContactDao
    abstract fun geoPointDao(): GeoPointDao
    abstract fun getUserDao() : GeoUserDao

    companion object {
        private const val DATABASE_NAME = "geo_db"
        @Volatile
        private var INSTANCE: GeoDatabase? = null

        fun getInstance(context: Context): GeoDatabase? {
            INSTANCE ?: synchronized(this) {
                INSTANCE = Room.databaseBuilder(
                    context,
                    GeoDatabase::class.java,
                    DATABASE_NAME
                ).allowMainThreadQueries().build()
            }
            return INSTANCE
        }
    }

}