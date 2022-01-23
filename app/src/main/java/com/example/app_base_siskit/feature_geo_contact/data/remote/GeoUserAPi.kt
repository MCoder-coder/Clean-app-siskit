package com.example.app_base_siskit.feature_geo_point.data.remote

import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GeoUserAPi {
    @GET("usuarios.php?hash={hash}&timestamp=0")
    suspend fun getUser(@Path("hash") hash : String ) : Response<>
}