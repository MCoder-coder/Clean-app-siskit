package com.example.app_base_siskit.feature_geo_point

import android.content.Context
import com.example.app_base_siskit.utils.SharedPrefs
import java.security.MessageDigest
import javax.inject.Inject

class RouteHash (context: Context){


    var sharedPrefs: SharedPrefs = SharedPrefs(context)


    fun newRouteHash(context: Context) {
        val temp_string = sharedPrefs.getEmail() + "" + System.currentTimeMillis()
        sharedPrefs.saveHash(temp_string.toMD5())

    }

    fun getRouteHash(): String {
        val prefs = sharedPrefs.getRouteHash()
        val temp_route_hash = prefs
        return temp_route_hash
    }

    private fun String.toMD5(): String {
        val bytes = MessageDigest.getInstance("MD5").digest(this.toByteArray())
        return bytes.toHex()
    }

    private fun ByteArray.toHex(): String {
        return joinToString("") { "%02x".format(it) }
    }

}