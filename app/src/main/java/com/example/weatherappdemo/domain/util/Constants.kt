package com.example.weatherappdemo.domain.util

object Constants {

    object UrlConstants {
        const val BASE_URL = "https://api.open-meteo.com/"

        const val HOURLY = "v1/forecast" +
                "?hourly=temperature_2m" +
                "&hourly=weathercode" +
                "&hourly=relativehumidity_2m" +
                "&hourly=windspeed_10m" +
                "&hourly=winddirection_10m" +
                "&hourly=pressure_msl"

        const val DAILY = "v1/forecast" +
                "?daily=temperature_2m_max" +
                "&daily=temperature_2m_min" +
                "&daily=precipitation_sum" +
                "&daily=weathercode" +
                "&daily=sunrise" +
                "&daily=sunset"
    }

    object WorkerConstants {
        const val ERROR_MESSAGE = "error_message_key"
    }

    object DataStoreConstants{
        const val FILE_URI = "file_uri_key"
        const val FILE_NAME = "data.txt"
        const val PREF_NAME = "pref_settings"
        const val CURRENT_LATITUDE = "current_latitude"
        const val CURRENT_LONGITUDE = "current_longitude"
        const val DEFAULT_LAT = 51.5002
        const val DEFAULT_LONG = -0.1262
    }
}