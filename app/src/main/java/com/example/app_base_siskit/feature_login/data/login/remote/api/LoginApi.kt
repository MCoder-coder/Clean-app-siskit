package com.example.app_base_siskit.feature_login.data.login.remote.api

import com.example.app_base_siskit.feature_login.data.common.utils.WrappedResponse
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginDto
import com.example.app_base_siskit.feature_login.data.login.remote.dto.LoginRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {

    @FormUrlEncoded
    @POST("login.php")
    suspend fun postLogin( @Field("email") email: String,
                           @Field("password") password: String): Response<WrappedResponse<LoginDto>>

    companion object {
        val PROTOCOL = "http://"
        val BASE_URL: String = "http://demo-distribuidora.siskit.com/sktsellerapi/"
    }

}