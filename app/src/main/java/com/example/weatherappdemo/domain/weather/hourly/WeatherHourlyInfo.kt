package com.example.weatherappdemo.domain.weather.hourly

data class WeatherHourlyInfo(
    val hourlyWeatherData: Map<Int, List<WeatherHourlyData>>,
    val currentWeatherData: WeatherHourlyData?
)
