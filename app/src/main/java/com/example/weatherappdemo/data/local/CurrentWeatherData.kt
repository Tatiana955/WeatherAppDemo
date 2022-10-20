package com.example.weatherappdemo.data.local

data class CurrentWeatherData(
    val time: String,
    val temperatureCelsius: Double,
    val pressure: Double,
    val windSpeed: Double,
    val directionWindDesc: String,
    val directionWindIconRes: String,
    val humidity: Double,
    val weatherDesc: String,
    val weatherIconRes: String
)