package com.example.app_base_siskit.feature_login.presentation.common

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.app_base_siskit.R


fun Context.showToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_LONG).show()
}

fun Context.showGenericAlertDialog(message: String){
    AlertDialog.Builder(this).apply {
        setMessage(message)
        setPositiveButton("Aceptar"){ dialog, _ ->
             dialog.dismiss()
        }
    }.show()
}