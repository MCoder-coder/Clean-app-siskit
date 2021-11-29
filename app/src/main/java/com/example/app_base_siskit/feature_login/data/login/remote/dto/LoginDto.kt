package com.example.app_base_siskit.feature_login.data.login.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginDto(
    @SerializedName("hash") val hash : String,
    @SerializedName("nombre") val nombre : String,
    @SerializedName("email") val email : String,
    @SerializedName("password") val password : String
)