package com.example.weatherappdemo.presentation.widgets

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.example.weatherappdemo.data.remote.WidgetWorker
import com.example.weatherappdemo.domain.location.LocationTracker
import com.example.weatherappdemo.domain.util.Constants.DataStoreConstants.CURRENT_LATITUDE
import com.example.weatherappdemo.domain.util.Constants.DataStoreConstants.CURRENT_LONGITUDE
import com.example.weatherappdemo.domain.widget.PreferencesManager
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class WidgetViewModel(
    private val locationTracker: LocationTracker,
    private val preferencesManager: PreferencesManager,
    private val workManager: WorkManager
) : ViewModel() {

    private val latitudeIndex = preferencesManager.latPref
    private val longitudeIndex = preferencesManager.longPref

    init {
        latitudeIndex.onEach { lat ->
            longitudeIndex.onEach { long ->
                setWork(lat, long)
            }.launchIn(viewModelScope)
        }.launchIn(viewModelScope)
    }

    fun setCategory() {
        viewModelScope.launch {
            locationTracker.getCurrentLocation()?.let {
                preferencesManager.setCurrentLocation(it.latitude, it.longitude)
            }
        }
    }

    private fun setWork(lat: Double, long: Double) {
        val inputData = Data.Builder()
            .putDouble(CURRENT_LATITUDE, lat)
            .putDouble(CURRENT_LONGITUDE, long)
            .build()
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val work = PeriodicWorkRequestBuilder<WidgetWorker>(1, TimeUnit.HOURS)
            .setConstraints(constraints)
            .setInputData(inputData)
            .build()
        workManager.enqueue(work)
    }
}