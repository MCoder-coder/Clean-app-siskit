package com.example.app_base_siskit.feature_geo_contact.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geo_contact")
data class GeoContactEntity(
    @PrimaryKey val ID: Int,
    val ACTIVIDAD: String,
    val CHECKCONTACTOJDLINK: Int,
    val CHECKRECLAMO: Int,
    val CHECKVENTAPOTENCIAL: Int,
    val CREATEDAT: Int,
    val EMAIL: String,
    val ERRORDETAIL: String,
    val ESCLIENTEJD: Int,
    val HASERROR: Int,
    val ISDELETED: Int,
    val ISLOCAL: Int,
    val ISUPLOADED: Int,
    val LATITUDE: Double,
    val LONGITUDE: Double,
    val MAQUINARIA: String,
    val NOMBRE: String,
    val RECLAMODESCRIPCION: String,
    val RECLAMOUSUARIOID: Int,
    val SERVERID: Int,
    val TELEFONO: String,
    val UPDATEDAT: Int,
    val VENTADESCRIPCION: String,
    val VENTAMONTOAPROXIMADO: Double,
    val VENTAVENDEDORID: Int
)