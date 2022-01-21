package com.example.app_base_siskit.feature_geo_point.data.repository

import android.app.Application
import android.content.Context
import android.os.AsyncTask
import com.example.app_base_siskit.common.GeoDatabase
import com.example.app_base_siskit.feature_geo_point.data.local.dao.GeoPointDao
import com.example.app_base_siskit.feature_geo_point.data.local.entity.GeoPointEntity

class PointRepo(context: Context){
    private val geoDatabase : GeoDatabase = GeoDatabase.getInstance(context)!!
    fun insertPoint(pointEntity: GeoPointEntity){
        geoDatabase.geoPointDao().insertGeoPoint(pointEntity)
    }


    private class InsertAsyncTask(private val geoPointDao: GeoPointDao) :
        AsyncTask<GeoPointEntity, Void, Void>() {
        override fun doInBackground(vararg points: GeoPointEntity?): Void? {
            for (point in points) {
                if (point != null) geoPointDao.insertGeoPoint(point)
            }
            return null
        }
    }
}