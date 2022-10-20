package com.example.weatherappdemo.data.remote

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.appwidget.GlanceAppWidgetManager
import androidx.glance.appwidget.state.updateAppWidgetState
import androidx.glance.appwidget.updateAll
import androidx.work.*
import com.example.weatherappdemo.domain.repository.WeatherRepository
import com.example.weatherappdemo.domain.util.Constants.DataStoreConstants.CURRENT_LATITUDE
import com.example.weatherappdemo.domain.util.Constants.DataStoreConstants.CURRENT_LONGITUDE
import com.example.weatherappdemo.domain.util.Constants.DataStoreConstants.DEFAULT_LAT
import com.example.weatherappdemo.domain.util.Constants.DataStoreConstants.DEFAULT_LONG
import com.example.weatherappdemo.domain.util.Constants.DataStoreConstants.FILE_NAME
import com.example.weatherappdemo.domain.util.Constants.DataStoreConstants.FILE_URI
import com.example.weatherappdemo.domain.util.Constants.WorkerConstants.ERROR_MESSAGE
import com.example.weatherappdemo.presentation.widgets.WeatherWidget
import java.io.File
import java.io.FileOutputStream

class WidgetWorker(
    private val context: Context,
    workerParameters: WorkerParameters,
    private val repository: WeatherRepository
) : CoroutineWorker(context, workerParameters) {

    override suspend fun doWork(): Result {
        return try {
            val currentLat = inputData.getDouble(CURRENT_LATITUDE, DEFAULT_LAT)
            val currentLong = inputData.getDouble(CURRENT_LONGITUDE, DEFAULT_LONG)
            val response = repository.getWeatherHourlyData(
                lat = currentLat,
                long = currentLong
            ).data?.currentWeatherData
            response?.let { data ->
                val dataFile = File(context.filesDir, FILE_NAME)
                FileOutputStream(dataFile).use { stream ->
                    stream.write(("${data.time}\n" +
                            "${data.temperatureCelsius}\n" +
                            "${data.pressure}\n" +
                            "${data.windSpeed}\n" +
                            "${data.windDirectionType.directionDesc}\n" +
                            "${data.windDirectionType.iconRes}\n" +
                            "${data.humidity}\n" +
                            "${data.weatherType.weatherDesc}\n" +
                            "${data.weatherType.iconRes}\n"
                            ).toByteArray()
                    )
                }
                context.filesDir.listFiles()?.let {
                    for (file in it) {
                        if (file.path.endsWith(".txt") && file.path != dataFile.path)
                            file.delete()
                    }
                }
                updateWidget(dataFile.path)
                Result.success()
            } ?: Result.failure(workDataOf(ERROR_MESSAGE to "Error occurred while retrieving object."))
        } catch (e: Exception) {
            Result.failure(workDataOf(ERROR_MESSAGE to e.localizedMessage))
        }
    }

    private suspend fun updateWidget(data: String) {
        GlanceAppWidgetManager(context).getGlanceIds(WeatherWidget::class.java).forEach { glanceId ->
            updateAppWidgetState(context, glanceId) { pref ->
                pref[stringPreferencesKey(FILE_URI)] = data
            }
            WeatherWidget().updateAll(context)
        }
    }
}