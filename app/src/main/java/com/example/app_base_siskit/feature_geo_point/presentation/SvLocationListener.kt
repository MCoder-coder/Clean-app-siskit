package com.example.app_base_siskit.feature_geo_point.presentation

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.location.Location
import android.location.LocationListener
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.viewModelScope
import com.example.app_base_siskit.feature_geo_point.ConstantsGeoPoints
import com.example.app_base_siskit.feature_geo_point.RouteHash
import com.example.app_base_siskit.feature_geo_point.data.local.entity.GeoPointEntity
import com.example.app_base_siskit.feature_geo_point.data.repository.PointRepo
import com.example.app_base_siskit.utils.SharedPrefs
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


class SvLocationListener (provider: String, c: Context) : LocationListener {


    internal var mLastLocation: Location
    var context: Context = c



    init {
        Log.e(ConstantsGeoPoints.TAG, "LocationListener $provider")
        mLastLocation = Location(provider)


    }
    override fun onLocationChanged(location: Location) {

        val pointRepo = PointRepo(context)
        val sharedPrefs = SharedPrefs(context)
        val routeHash = RouteHash(context)
        routeHash.newRouteHash(context)
        Log.e(ConstantsGeoPoints.TAG, "onLocationChanged: $location")
        mLastLocation.set(location)

        Log.v(
            "LastLocation",
            mLastLocation.latitude.toString() + "  " + mLastLocation.longitude.toString()
        )
        Log.d(ConstantsGeoPoints.TAG, "mLastLocation: " + mLastLocation.toString())
        // val prefRouteHash = this.getRouteHash()
        //  Log.d(TAG, "Route Hash: " + prefRouteHash)

        // guardar geopoint
        val point = GeoPointEntity( 0 ,
            location.latitude , location.longitude ,
            location.accuracy , location.speed ,
            routeHash.getRouteHash() , false,
            "" , false ,
            true , System.currentTimeMillis() ,
            System.currentTimeMillis())
        pointRepo.insertPoint(point)

        pointRepo.run {

        }
        // Log.d(TAG, "lastGeoTrack:" + lastGeoTrack.toString())

    }
}