package com.example.app_base_siskit.feature_login.data.login.remote

import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginResponse
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginResponseBase
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {

    @FormUrlEncoded
    @POST("login.php")
    suspend fun postLogin( @Field("email") email: String,
                           @Field("password") password: String): Response<LoginResponse<LoginResponseBase>>



}