package com.example.app_base_siskit.feature_geo_point.domain

import android.content.Context
import android.location.Location
import android.util.Log

import com.example.app_base_siskit.feature_geo_point.ConstantsGeoPoints
import com.example.app_base_siskit.feature_geo_point.data.local.dao.GeoPointDao
import com.example.app_base_siskit.feature_geo_point.data.local.entity.GeoPointEntity

class SvLocationListenerUseCase (provider: String, c: Context) : android.location.LocationListener {

    lateinit var geoPointDao: GeoPointDao
    internal var mLastLocation: Location
    var context: Context = c

    init {
        Log.e(ConstantsGeoPoints.TAG, "LocationListener $provider")
        mLastLocation = Location(provider)
    }
    override fun onLocationChanged(location: Location) {

        Log.e(ConstantsGeoPoints.TAG, "onLocationChanged: $location")
        if (location != null) {
                mLastLocation.set(location)
                Log.v(
                    "LastLocation",
                    mLastLocation.latitude.toString() + "  " + mLastLocation.longitude.toString()
                )
                Log.d(ConstantsGeoPoints.TAG, "mLastLocation: " + mLastLocation.toString())
                // val prefRouteHash = this.getRouteHash()
                //  Log.d(TAG, "Route Hash: " + prefRouteHash)
                val geoPointEntity =GeoPointEntity(
//                    val ACCURACY: Double,
//            val CREATEDAT: Int,
//            val ERRORDETAIL: String,
//            val HASERROR: Int,
//            val ISDELETED: Int,
//            val ISLOCAL: Int,
//            val ISUPLOADED: Int,
//            var LATTITUDE: Double,
//            val LONGITUDE: Double,
//            val ROUTEHASH: String,
//            val SERVERID: Int,
//            val SPEED: Double,
//            val UPDATEDAT: Int
                )
                geoPointDao.insertGeoPoint()
                // Log.d(TAG, "lastGeoTrack:" + lastGeoTrack.toString())
        }

    }
}