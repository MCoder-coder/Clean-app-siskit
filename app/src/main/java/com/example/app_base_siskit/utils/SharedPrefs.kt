package com.example.app_base_siskit.utils

import android.content.Context
import android.content.SharedPreferences

@Suppress("UNCHECKED_CAST")
class SharedPrefs (private val context: Context) {
    companion object {
        private const val PREF = "MyAppPrefName"
        private const val PREF_HASH = "user_hash"
        private const val PREF_NOMBRE = "nombre"
        private const val PREF_EMAIL = "email"
    }

    private val sharedPref: SharedPreferences = context.getSharedPreferences(PREF, Context.MODE_PRIVATE)

    fun saveEmail(email : String){
        put(PREF_EMAIL , email)
    }
    fun saveNombre(nombre : String){
        put(PREF_NOMBRE , nombre)
    }
    fun saveHash(hash: String){
        put(PREF_HASH, hash)
    }
    fun getEmail() : String{
       return get(PREF_EMAIL , String::class.java)
    }
    fun getNombre() : String{
       return get(PREF_NOMBRE , String::class.java)
    }
    fun getHash() : String {
        return get(PREF_HASH, String::class.java)
    }

    private fun <T> get(key: String, clazz: Class<T>): T =
        when (clazz) {
            String::class.java -> sharedPref.getString(key, "")
            Boolean::class.java -> sharedPref.getBoolean(key, false)
            Float::class.java -> sharedPref.getFloat(key, -1f)
            Double::class.java -> sharedPref.getFloat(key, -1f)
            Int::class.java -> sharedPref.getInt(key, -1)
            Long::class.java -> sharedPref.getLong(key, -1L)
            else -> null
        } as T

    private fun <T> put(key: String, data: T) {
        val editor = sharedPref.edit()
        when (data) {
            is String -> editor.putString(key, data)
            is Boolean -> editor.putBoolean(key, data)
            is Float -> editor.putFloat(key, data)
            is Double -> editor.putFloat(key, data.toFloat())
            is Int -> editor.putInt(key, data)
            is Long -> editor.putLong(key, data)
        }
        editor.apply()
    }

    fun clear() {
        sharedPref.edit().run {
            remove(PREF_HASH)
        }.apply()
    }
}