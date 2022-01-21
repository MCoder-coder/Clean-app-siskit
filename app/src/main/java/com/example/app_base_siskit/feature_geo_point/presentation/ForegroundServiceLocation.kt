package com.example.app_base_siskit.feature_geo_point.presentation

import android.app.*
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.fragment.app.activityViewModels
import com.example.app_base_siskit.MainActivity
import com.example.app_base_siskit.R
import com.example.app_base_siskit.feature_geo_point.ConstantsGeoPoints

import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


class ForegroundServiceLocation : Service() {


    var TAG = "ForegroundServiceLocation"
    private var mLocationManager: LocationManager? = null


    override fun onBind(intent: Intent?): IBinder? {
        return null
    }


    override fun onCreate() {

        Log.d(TAG,"Servicio creado...")
        initializeLocationManager()
        requestLocationNotificationUpdate()
    }

    private fun requestLocationNotificationUpdate(){
        try {
            mLocationManager!!.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, ConstantsGeoPoints.LOCATION_INTERVAL.toLong(), ConstantsGeoPoints.LOCATION_DISTANCE,
                mLocationListeners[1])
        } catch (ex: java.lang.SecurityException) {
            Log.i(TAG, "fail to request location update, ignore", ex)
        } catch (ex: IllegalArgumentException) {
            Log.d(TAG, "network provider does not exist, " + ex.message)
        }

        try {
            mLocationManager!!.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, ConstantsGeoPoints.LOCATION_INTERVAL.toLong(),
                ConstantsGeoPoints.LOCATION_DISTANCE,
                mLocationListeners[0])
        } catch (ex: java.lang.SecurityException) {
            Log.i(TAG, "fail to request location update, ignore", ex)
        } catch (ex: IllegalArgumentException) {
            Log.d(TAG, "gps provider does not exist " + ex.message)
        }
    }

    private fun initializeLocationManager() {
        Log.e(TAG, "initializeLocationManager")
        if (mLocationManager == null) {
            mLocationManager = applicationContext.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        Log.d(TAG, "creando notificacion...")

        val channelId =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                createNotificationChannel("my_service", "My Background Service")
            } else {
                ""
            }
        Log.d(TAG, "channerlId: "+ channelId);

        createNotificationStartForeground(channelId)
        return Service.START_STICKY
    }

    var mLocationListeners = arrayOf(
        SvLocationListener(LocationManager.GPS_PROVIDER, this), SvLocationListener(
            LocationManager.NETWORK_PROVIDER, this)
    )


    private fun createNotificationStartForeground(channelId: String){
        val icon = BitmapFactory.decodeResource(resources, R.drawable.ic_notification_navarro)
        val contentIntent = PendingIntent.getActivity(this, 0, Intent(this, MainActivity::class.java), 0)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentTitle("INNOVArro esta en ejecucion")
            .setTicker("INNOVArro ...")
            .setContentText("Toque para agregar un nuevo Geocontacto")
            .setSmallIcon(R.drawable.ic_notification_navarro)
            .setLargeIcon(Bitmap.createScaledBitmap(icon, 128, 128, false))
            .setOngoing(true)
            .setContentIntent(contentIntent)
            .build()
        Log.d(TAG, "notificacion: "+ notification.toString());
        Log.d(TAG, "startForeground(): ");
        startForeground(1001, notification)

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String): String{
        val chan = NotificationChannel(channelId,
            channelName, NotificationManager.IMPORTANCE_NONE)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE
        val service = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        service.createNotificationChannel(chan)
        return channelId
    }

    override fun onDestroy() {
        removeNotificationUpdate()
    }

    fun removeNotificationUpdate(){
        Log.d(TAG, "servicio detenido...")
        // Implementando ubicacion
        if (mLocationManager != null) {
            for (i in mLocationListeners.indices) {
                try {
                    mLocationManager!!.removeUpdates(mLocationListeners[i])
                } catch (ex: Exception) {
                    Log.i(TAG, "fail to remove location listners, ignore", ex)
                }
            }
        }
    }
    override fun onTaskRemoved(rootIntent: Intent) {

        Log.d("CERRANDOAPP", "La app esta siendo cerrada y se ejecuta la finalizacion del servicio de ubicacion")
        //stop service
        stopSelf()
    }





}