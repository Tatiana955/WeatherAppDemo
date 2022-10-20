package com.example.weatherappdemo.domain.weather.hourly

import com.example.weatherappdemo.domain.weather.WeatherType
import com.example.weatherappdemo.domain.weather.WindDirectionType
import java.time.LocalDateTime

data class WeatherHourlyData(
    val time: LocalDateTime,
    val temperatureCelsius: Double,
    val pressure: Double,
    val windSpeed: Double,
    val windDirectionType: WindDirectionType,
    val humidity: Double,
    val weatherType: WeatherType
)
