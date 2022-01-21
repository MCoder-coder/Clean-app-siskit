package com.example.app_base_siskit.feature_geo_point.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geo_point")
data class GeoPointEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    var lat: Double = 0.0,
    var lon: Double = 0.0,
    var accu: Float = 0.0F,
    var vel: Float = 0.0F,

    var routehash: String = "xxx_init_route",
    var error: Boolean = false,
    var error_detail: String = "",
    var deleted: Boolean = false,
    var local: Boolean = true,
    var created_at: Long = 0L,
    var updated_at: Long = 0L,
    var uploaded: Boolean = false,
)