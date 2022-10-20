package com.example.weatherappdemo.domain.repository

import com.example.weatherappdemo.domain.util.Resource
import com.example.weatherappdemo.domain.weather.daily.WeatherDailyInfo
import com.example.weatherappdemo.domain.weather.hourly.WeatherHourlyInfo

interface WeatherRepository {

    suspend fun getWeatherHourlyData(lat: Double, long: Double): Resource<WeatherHourlyInfo>

    suspend fun getWeatherDailyData(lat: Double, long: Double, timeZone: String): Resource<WeatherDailyInfo>
}