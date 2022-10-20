package com.example.weatherappdemo.presentation.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherappdemo.domain.location.LocationTracker
import com.example.weatherappdemo.domain.repository.WeatherRepository
import com.example.weatherappdemo.domain.util.Resource
import com.example.weatherappdemo.presentation.WeatherState
import kotlinx.coroutines.launch
import java.util.*

class WeatherViewModel(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
): ViewModel() {

    var state by mutableStateOf(WeatherState())
        private set

    fun loadWeatherHourlyInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                when(val result = repository.getWeatherHourlyData(
                    lat = location.latitude,
                    long = location.longitude)
                ) {
                    is Resource.Success -> {
                        state = state.copy(
                            weatherHourlyInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            weatherHourlyInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: kotlin.run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                )
            }
        }
    }

    fun loadWeatherDailyInfo() {
        viewModelScope.launch {
            state = state.copy(
                isLoading = true,
                error = null
            )
            locationTracker.getCurrentLocation()?.let { location ->
                when(val result = repository.getWeatherDailyData(
                    lat = location.latitude,
                    long = location.longitude,
                    timeZone = TimeZone.getDefault().toZoneId().toString()
                    )
                ) {
                    is Resource.Success -> {
                        state = state.copy(
                            weatherDailyInfo = result.data,
                            isLoading = false,
                            error = null
                        )
                    }
                    is Resource.Error -> {
                        state = state.copy(
                            weatherDailyInfo = null,
                            isLoading = false,
                            error = result.message
                        )
                    }
                }
            } ?: kotlin.run {
                state = state.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                )
            }
        }
    }
}