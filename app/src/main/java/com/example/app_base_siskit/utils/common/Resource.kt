package com.example.app_base_siskit.utils.common

sealed class Resource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : Resource<T>(data)
    class Error<T>(mensaje: String, data: T? = null) : Resource<T>(data, mensaje)
    class Loading<T> : Resource<T>()
}
