package com.example.app_base_siskit.feature_login.domain.login.entity

data class LoginEntity (
    val email: String,
    val password: String,
    val hash: String,
    val id: String,
    val nombre: String
)