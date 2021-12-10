package com.example.app_base_siskit.utils

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AlertDialog

interface DialogListener{
    fun setPositiveButton()
}
class ShowDialog {

    fun showDialog(context: Context , registerReceiver : Intent? , beginDownload: Unit){
        val builder = AlertDialog.Builder(context)
        builder.setMessage("Parace que aun no has descargado el mapa para uso off-line, ¿desea descargarlo ahora?")
            .setCancelable(false)
            .setPositiveButton("SI!") { dialog , id ->
                registerReceiver
                beginDownload
            }
            .setNegativeButton("Ahora NO!") { dialog, id ->
                dialog.cancel()
            }
        val alert: AlertDialog = builder.create()
        alert.show()
    }

    fun setPositiveButton(context: Context, registerReceiver: Intent?, beginDownload: Unit){
        showDialog(context, registerReceiver, beginDownload)
    }



}