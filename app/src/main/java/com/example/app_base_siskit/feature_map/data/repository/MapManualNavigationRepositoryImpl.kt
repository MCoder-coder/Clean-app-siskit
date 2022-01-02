package com.example.app_base_siskit.feature_map.data.repository

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.location.Location
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.example.app_base_siskit.feature_map.domain.repository.MapManualNavigationModeRepository
import org.mapsforge.map.android.view.MapView


class MapManualNavigationRepositoryImpl : MapManualNavigationModeRepository {
    @SuppressLint("ClickableViewAccessibility")
    override fun gestureDetector(context: Context, mapView: MapView, isInManualAddMode : Boolean) : Boolean {
         val gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
                //do something
                Log.d(TAG, "onSingleTapConfirmed")
                if(isInManualAddMode){
                    Log.d(TAG, "onSingleTapConfirmed -> Click Acepted")
                    val xpos = e.getX()
                    val ypos = e.getY()

                    val pr = mapView.mapViewProjection
                    val clickLatlong = pr?.fromPixels(xpos.toDouble(), ypos.toDouble());
                    if(clickLatlong!= null){
                        mapView.setCenter(clickLatlong)
                       // addMarker(clickLatlong)
                        val manualLocation = Location("")
                        manualLocation.latitude = clickLatlong.latitude
                        manualLocation.longitude = clickLatlong.longitude
                        //addNewGeoContactoManual(manualLocation)
                    }

                    Log.d(TAG, "ACTION_UP -> centrado al click")
                    return true
                }else{
                    Log.d(TAG, "onSingleTapConfirmed -> Click ignored (are not in manualMode)")
                    return false
                }
            }
            override fun onLongPress(e: MotionEvent) {
                super.onLongPress(e)
            }
            override fun onDoubleTap(e: MotionEvent): Boolean {
                return super.onDoubleTap(e)
            }



        })

        mapView.setOnTouchListener(View.OnTouchListener { v, ev ->
            val actionType = ev.action
            Log.d(TAG, "OnTouchListener-> actionType: " + actionType)
            val xxx = gestureDetector.onTouchEvent(ev)
            return@OnTouchListener xxx

        })

        return isInManualAddMode
    }

}