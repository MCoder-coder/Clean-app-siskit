package com.example.app_base_siskit.feature_geo_contact.data.remote.dto

import com.example.app_base_siskit.feature_geo_contact.data.local.entity.GeoUserEntity

data class GeoUserContainer(val user : List<UserDto>)

data class UserDto(
    val id: Int,
    val email: String,
    val nombre: String,
    val sistema_id: Int,
    val telefono: String,
    val usuarios_tipos_id: String
)

fun GeoUserContainer.asDatabaseModel() : List<GeoUserEntity>{
    return user.map {
        GeoUserEntity(
            email = it.email,
            id = it.id.toInt(),
            nombre = it.nombre,
            sistema_id = it.sistema_id.toInt(),
            telefono = it.telefono,
            usuarios_tipos_id = it.usuarios_tipos_id
        )
    }
}
