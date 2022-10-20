package com.example.weatherappdemo.data.remote.daily

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDataDailyDto(
    val time: List<String>,
    val temperature_2m_max: List<Double>,
    val temperature_2m_min: List<Double>,
    val precipitation_sum: List<Double>,
    val weathercode: List<Double>,
    val sunrise: List<String>,
    val sunset: List<String>
)