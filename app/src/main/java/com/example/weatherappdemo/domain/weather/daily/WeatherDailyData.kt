package com.example.weatherappdemo.domain.weather.daily

import com.example.weatherappdemo.domain.weather.WeatherType
import java.time.LocalDate

data class WeatherDailyData(
    val date: LocalDate,
    val temperatureCelsius_max: Double,
    val temperatureCelsius_min: Double,
    val precipitation_sum: Double,
    val sunrise: String,
    val sunset: String,
    val weatherType: WeatherType
)
