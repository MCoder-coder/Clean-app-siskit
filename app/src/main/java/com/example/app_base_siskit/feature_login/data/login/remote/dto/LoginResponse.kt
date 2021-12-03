package com.example.app_base_siskit.feature_login.data.login.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginResponse<T>(
    @SerializedName("mensaje") var mensaje : String,
    @SerializedName("estado") var estado : String,
    @SerializedName("data") var loginResponseDataBase : LoginResponseDataBase
)

