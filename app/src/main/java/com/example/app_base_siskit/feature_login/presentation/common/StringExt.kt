package com.example.app_base_siskit.feature_login.presentation.common

import android.util.Patterns

fun String.isEmail() : Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}