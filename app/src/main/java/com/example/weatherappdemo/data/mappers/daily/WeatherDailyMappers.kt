package com.example.weatherappdemo.data.mappers.daily

import com.example.weatherappdemo.data.remote.daily.WeatherDailyDto
import com.example.weatherappdemo.data.remote.daily.WeatherDataDailyDto
import com.example.weatherappdemo.domain.weather.WeatherType
import com.example.weatherappdemo.domain.weather.daily.WeatherDailyData
import com.example.weatherappdemo.domain.weather.daily.WeatherDailyInfo
import java.time.LocalDate
import java.time.format.DateTimeFormatter

private data class IndexedWeatherDailyData(
    val index: Int,
    val data: WeatherDailyData
)

private fun WeatherDataDailyDto.toWeatherDataMap(): Map<Int, List<WeatherDailyData>> {
    return time.mapIndexed { index, time ->
        val temperature_max = temperature_2m_max[index]
        val temperature_min = temperature_2m_min[index]
        val weatherCode = weathercode[index].toInt()
        val precipitation_sum = precipitation_sum[index]
        val sunrise = sunrise[index]
        val sunset = sunset[index]
        IndexedWeatherDailyData(
            index = index,
            data = WeatherDailyData(
                date = LocalDate.parse(time, DateTimeFormatter.ISO_DATE),
                temperatureCelsius_max = temperature_max,
                temperatureCelsius_min = temperature_min,
                precipitation_sum = precipitation_sum,
                sunrise = sunrise,
                sunset = sunset,
                weatherType = WeatherType.fromWMO(weatherCode)
            )
        )
    }.groupBy {
        it.index / 7
    }.mapValues {
        it.value.map { indexedWeatherData ->
            indexedWeatherData.data
        }
    }
}

fun WeatherDailyDto.toWeatherDailyInfo(): WeatherDailyInfo {
    val weatherDataMap = daily.toWeatherDataMap()
    return WeatherDailyInfo(
        dailyWeatherData = weatherDataMap
    )
}