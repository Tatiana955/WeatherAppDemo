package com.example.weatherappdemo.data.repository

import android.util.Log
import com.example.weatherappdemo.data.mappers.daily.toWeatherDailyInfo
import com.example.weatherappdemo.data.mappers.hourly.toWeatherHourlyInfo
import com.example.weatherappdemo.data.remote.daily.WeatherDailyDto
import com.example.weatherappdemo.data.remote.hourly.WeatherHourlyDto
import com.example.weatherappdemo.domain.repository.WeatherRepository
import com.example.weatherappdemo.domain.util.Constants.UrlConstants.DAILY
import com.example.weatherappdemo.domain.util.Constants.UrlConstants.HOURLY
import com.example.weatherappdemo.domain.util.Resource
import com.example.weatherappdemo.domain.weather.daily.WeatherDailyInfo
import com.example.weatherappdemo.domain.weather.hourly.WeatherHourlyInfo
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*

class WeatherRepositoryImpl(
    private val httpClient: HttpClient
): WeatherRepository {

    override suspend fun getWeatherHourlyData(
        lat: Double,
        long: Double
    ): Resource<WeatherHourlyInfo> {
        return try {
            val data = httpClient.get {
                url(HOURLY)
                parameter("latitude", lat)
                parameter("longitude", long)
            }.body<WeatherHourlyDto>().toWeatherHourlyInfo()
            Resource.Success(data)
        } catch(e: Exception) {
            Log.e("!!!", e.toString())
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }

    override suspend fun getWeatherDailyData(
        lat: Double,
        long: Double,
        timeZone: String
    ): Resource<WeatherDailyInfo> {
        return try {
            val data = httpClient.get {
                url(DAILY)
                parameter("latitude", lat)
                parameter("longitude", long)
                parameter("timezone", timeZone)
            }.body<WeatherDailyDto>().toWeatherDailyInfo()
            Resource.Success(data)
        } catch(e: Exception) {
            Log.e("!!!", e.message.toString())
            Resource.Error(e.message ?: "An unknown error occurred.")
        }
    }
}