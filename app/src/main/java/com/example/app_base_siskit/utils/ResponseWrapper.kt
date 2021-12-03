package com.example.app_base_siskit.feature_login.data.common.utils

import com.google.gson.annotations.SerializedName

data class DefaultResponse<T> (
    @SerializedName("mensaje") var mensaje : String,
    @SerializedName("estado") var estado : String,
    @SerializedName("data") var data : List<T>? = null
)

data class DefaultResponseWithErrors<T> (
    @SerializedName("mensaje") var mensaje : String,
    @SerializedName("estado") var estado : Boolean,
    @SerializedName("errors") var error : List<String>? = null,
    @SerializedName("data") var data : List<T>? = null
)