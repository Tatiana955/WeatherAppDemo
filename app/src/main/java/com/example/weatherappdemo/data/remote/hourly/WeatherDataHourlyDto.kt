package com.example.weatherappdemo.data.remote.hourly

import kotlinx.serialization.Serializable

@Serializable
data class WeatherDataHourlyDto(
    val time: List<String>,
    val temperature_2m: List<Double>,
    val weathercode: List<Double>,
    val relativehumidity_2m: List<Double>,
    val windspeed_10m: List<Double>,
    val pressure_msl: List<Double>,
    val winddirection_10m: List<Double?>
)