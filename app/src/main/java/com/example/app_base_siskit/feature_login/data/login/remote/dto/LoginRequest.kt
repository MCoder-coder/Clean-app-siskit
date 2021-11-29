package com.example.app_base_siskit.feature_login.data.login.remote.dto

import com.google.gson.annotations.SerializedName
import retrofit2.http.Field

data class LoginRequest(
    @Field("email") val email: String,
    @Field("password") val password: String
)

