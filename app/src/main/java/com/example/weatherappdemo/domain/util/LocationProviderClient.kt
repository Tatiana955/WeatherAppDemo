package com.example.weatherappdemo.domain.util

import android.app.Application
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

fun provideFusedLocationProviderClient(app: Application): FusedLocationProviderClient {
    return LocationServices.getFusedLocationProviderClient(app)
}