package com.example.weatherappdemo.data.remote.daily

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDailyDto(
    val daily: WeatherDataDailyDto
)