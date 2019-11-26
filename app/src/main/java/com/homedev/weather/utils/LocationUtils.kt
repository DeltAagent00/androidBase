package com.homedev.weather.utils

import android.content.Context
import android.location.Location
import com.google.android.gms.location.LocationServices

/**
 * Created by Alexandr Zheleznyakov on 2019-10-03.
 */
object LocationUtils {
    fun startGetLastLocation(context: Context, success: ((Location) -> Unit)? = null) {
        val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation.addOnSuccessListener { itLocation ->
            itLocation?.let {
                success?.invoke(it)
            }
        }
    }
}