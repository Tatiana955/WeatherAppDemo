package com.example.weatherappdemo.data.mappers.hourly

import com.example.weatherappdemo.data.remote.hourly.WeatherDataHourlyDto
import com.example.weatherappdemo.data.remote.hourly.WeatherHourlyDto
import com.example.weatherappdemo.domain.weather.hourly.WeatherHourlyData
import com.example.weatherappdemo.domain.weather.hourly.WeatherHourlyInfo
import com.example.weatherappdemo.domain.weather.WeatherType
import com.example.weatherappdemo.domain.weather.WindDirectionType
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

private data class IndexedWeatherHourlyData(
    val index: Int,
    val data: WeatherHourlyData
)

private fun WeatherDataHourlyDto.toWeatherDataMap(): Map<Int, List<WeatherHourlyData>> {
    return time.mapIndexed { index, time ->
        val temperature = temperature_2m[index]
        val weatherCode = weathercode[index].toInt()
        val windSpeed = windspeed_10m[index]
        val pressure = pressure_msl[index]
        val humidity = relativehumidity_2m[index]
        val windDirection = winddirection_10m[index]?.toInt()
        IndexedWeatherHourlyData(
            index = index,
            data = WeatherHourlyData(
                time = LocalDateTime.parse(time, DateTimeFormatter.ISO_DATE_TIME),
                temperatureCelsius = temperature,
                pressure = pressure,
                windSpeed = windSpeed,
                humidity = humidity,
                weatherType = WeatherType.fromWMO(weatherCode),
                windDirectionType = WindDirectionType.fromDir(windDirection)
            )
        )
    }.groupBy {
        it.index / 24
    }.mapValues {
        it.value.map { indexedWeatherData ->
            indexedWeatherData.data
        }
    }
}

fun WeatherHourlyDto.toWeatherHourlyInfo(): WeatherHourlyInfo {
    val weatherDataMap = hourly.toWeatherDataMap()
    val now = LocalDateTime.now()
    val currentWeatherData = weatherDataMap[0]?.find {
        val hour = if(now.minute < 30) now.hour else now.hour + 1
        it.time.hour == hour
    }
    return WeatherHourlyInfo(
        hourlyWeatherData = weatherDataMap,
        currentWeatherData = currentWeatherData
    )
}