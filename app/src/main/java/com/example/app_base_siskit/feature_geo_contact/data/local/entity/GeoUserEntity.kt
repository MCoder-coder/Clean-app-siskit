package com.example.app_base_siskit.feature_geo_contact.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geo_user")
data class GeoUserEntity(
    @PrimaryKey val ID: Int,
    val EMAIL: String,
    val ISDELETED: Int,
    val NAME: String,
    val SERVERID: Int,
    val SYSTEMID: Int,
    val USERTYPEID: String
)