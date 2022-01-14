package com.example.app_base_siskit.feature_map.utils

import android.location.Location
import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class LocationDataClass(val myLocation: Location ) : Parcelable
