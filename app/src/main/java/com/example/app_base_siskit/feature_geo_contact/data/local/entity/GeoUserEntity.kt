package com.example.app_base_siskit.feature_geo_contact.data.local.entity

import androidx.lifecycle.LiveData
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.app_base_siskit.feature_geo_contact.domain.entity.UserEntity



@Entity(tableName = "geo_user")
data class GeoUserEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,
    val email: String = "",
    val nombre: String = "",
    val sistema_id: Int = 0,
    val telefono: String = "",
    val usuarios_tipos_id: String = ""
)

/*
fun List<GeoUserEntity>.asDomainModel() : List<UserEntity>{
        return map{
            UserEntity(
                id = it.id.toString(),
                email = it.email,
                nombre = it.nombre,
                telefono = it.telefono,
                sistema_id = it.sistema_id.toString(),
                usuarios_tipos_id = it.usuarios_tipos_id
            )
        }
}*/
