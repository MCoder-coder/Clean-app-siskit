package com.example.app_base_siskit.utils

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.appcompat.app.AlertDialog
import com.example.app_base_siskit.feature_map.utils.RequestAllRequiredPermission

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

    fun showRequiredPermissionsDialog(context: Context) {
        Log.d(ContentValues.TAG,"showRequiredPermissionsDialog()")
        val hasAllRequiredPermssions = RequestAllRequiredPermission().hasAllRequiredPermssions(context)
        var permissionsAlertDialog: AlertDialog? = null
        if(!hasAllRequiredPermssions) {
            val builder = AlertDialog.Builder(context)
            builder.setMessage("Para utilizar la app es necesario que acepte los permisos de: Ubicacion, Lectura y Escritura; y mantener el GPS encendido.")
                .setCancelable(false)
                .setPositiveButton("OK, Entendido!") { dialog, id ->
                    RequestAllRequiredPermission().requestAllRequiredPermssions(context)

                }
                .setNegativeButton("Cerrar App") { dialog, id ->
                    dialog.cancel()
                }
            permissionsAlertDialog = builder.create()
            permissionsAlertDialog.show()
            permissionsAlertDialog.setOnCancelListener {
                it.dismiss()
                //  finish()
            }
        }else{
            // initService()
        }

    }

}