package com.example.weatherappdemo.presentation

import com.example.weatherappdemo.domain.weather.daily.WeatherDailyInfo
import com.example.weatherappdemo.domain.weather.hourly.WeatherHourlyInfo

data class WeatherState(
    val weatherHourlyInfo: WeatherHourlyInfo? = null,
    val weatherDailyInfo: WeatherDailyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
