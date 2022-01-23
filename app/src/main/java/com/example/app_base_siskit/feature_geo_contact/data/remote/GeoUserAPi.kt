package com.example.app_base_siskit.feature_geo_contact.data.remote

import com.example.app_base_siskit.feature_geo_contact.data.remote.dto.UserDto
import com.example.app_base_siskit.feature_login.data.common.utils.DefaultResponse
import com.example.app_base_siskit.feature_login.presentation.common.BaseResult
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GeoUserAPi {
    @GET("usuarios.php?" + "hash=" + "df5d37ebd1b86929165960a240f810e1" + "&timestamp=0")
    suspend fun getUser(
        ) : Response<DefaultResponse<UserDto>>
}

//val df5d37ebd1b86929165960a240f810e1