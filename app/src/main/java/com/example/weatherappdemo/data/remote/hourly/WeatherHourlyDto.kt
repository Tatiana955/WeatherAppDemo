package com.example.weatherappdemo.data.remote.hourly

import kotlinx.serialization.Serializable

@Serializable
data class WeatherHourlyDto(
    val hourly: WeatherDataHourlyDto
)
