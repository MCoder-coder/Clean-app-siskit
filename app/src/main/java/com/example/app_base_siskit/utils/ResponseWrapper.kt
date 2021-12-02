package com.example.app_base_siskit.feature_login.data.common.utils

import com.example.app_base_siskit.feature_login.data.login.remote.dto.Data
import com.google.gson.annotations.SerializedName

data class WrappedListResponse<T> (
    var code: Int,
    @SerializedName("mensaje") var mensaje : String,
    @SerializedName("estado") var estado : Boolean,
    @SerializedName("errors") var error : List<String>? = null,
    @SerializedName("data") var data : List<T>? = null
)


data class WrappedResponse<T> (
    @SerializedName("mensaje") var mensaje : String,
    @SerializedName("estado") var estado : String,
    @SerializedName("data") var data : Data
)