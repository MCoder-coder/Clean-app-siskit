package com.example.app_base_siskit.feature_login.data.login.utils

import com.example.app_base_siskit.feature_login.data.login.remote.dto.Data
import com.google.gson.annotations.SerializedName

data class WrappedResponseLogin<T>(
    @SerializedName("mensaje") var mensaje : String,
    @SerializedName("estado") var estado : String,
    @SerializedName("data") var data : Data
)

