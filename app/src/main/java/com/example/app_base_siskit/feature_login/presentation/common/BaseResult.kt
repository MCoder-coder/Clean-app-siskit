package com.example.app_base_siskit.feature_login.presentation.common

sealed class BaseResult <out T : Any, out U : Any> {
    data class Success <T: Any>(val data : T) : BaseResult<T, Nothing>()
    data class Error <T : Any>(val rawResponse: T) : BaseResult<Nothing, T>()
}