package com.example.app_base_siskit.feature_geo_point.data


import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GeoPointApi {

    @FormUrlEncoded
    @POST("geotracks_upload.php?")
    suspend fun postGeoPoint()
}