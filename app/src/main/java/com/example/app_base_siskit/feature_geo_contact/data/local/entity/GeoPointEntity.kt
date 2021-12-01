package com.example.app_base_siskit.feature_geo_contact.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geo_point")
data class GeoPointEntity(
    @PrimaryKey val ID: String,
    val ACCURACY: Double,
    val CREATEDAT: Int,
    val ERRORDETAIL: String,
    val HASERROR: Int,
    val ISDELETED: Int,
    val ISLOCAL: Int,
    val ISUPLOADED: Int,
    val LATTITUDE: Double,
    val LONGITUDE: Double,
    val ROUTEHASH: String,
    val SERVERID: Int,
    val SPEED: Double,
    val UPDATEDAT: Int
)