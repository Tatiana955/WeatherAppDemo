package com.example.weatherappdemo.domain.weather.daily

data class WeatherDailyInfo(
    val dailyWeatherData: Map<Int, List<WeatherDailyData>>
)