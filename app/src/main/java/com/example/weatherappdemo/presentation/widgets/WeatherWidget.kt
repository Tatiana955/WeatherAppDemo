package com.example.weatherappdemo.presentation.widgets

import com.example.weatherappdemo.data.local.CurrentWeatherData
import com.example.weatherappdemo.domain.util.Constants.DataStoreConstants.FILE_URI
import com.example.weatherappdemo.domain.widget.CustomGlanceStateDefinition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.glance.*
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.state.GlanceStateDefinition
import com.example.weatherappdemo.R
import java.io.File
import java.io.InputStream
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.layout.*
import androidx.glance.layout.Column
import androidx.glance.layout.Row
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import kotlin.math.roundToInt

class WeatherWidget : GlanceAppWidget(errorUiLayout = R.layout.widget_error_layout) {

    override val stateDefinition: GlanceStateDefinition<*>
        get() = CustomGlanceStateDefinition

    @Composable
    override fun Content() {
        val data = getData()
        Row(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = GlanceModifier
                .fillMaxWidth()
        ) {
            CurrentWeather(
                data = data
            )
            Spacer(modifier = GlanceModifier.size(40.dp))
            ListOfWeatherDetails(
                data = data
            )
        }
    }

    @Composable
    private fun getData(): CurrentWeatherData {
        val pref = currentState<Preferences>()
        val filePathString: String = remember {
            pref[stringPreferencesKey(FILE_URI)] ?: ""
        }
        val inputStream: InputStream = File(filePathString).inputStream()
        val lineList = mutableListOf<String>()
        inputStream.bufferedReader().forEachLine {
            lineList.add(it)
        }
        return CurrentWeatherData(
            time = lineList[0],
            temperatureCelsius = lineList[1].toDouble(),
            pressure = lineList[2].toDouble(),
            windSpeed = lineList[3].toDouble(),
            directionWindDesc = lineList[4],
            directionWindIconRes = lineList[5],
            humidity = lineList[6].toDouble(),
            weatherDesc = lineList[7],
            weatherIconRes = lineList[8]
        )
    }

    @Composable
    private fun CurrentWeather(
        data: CurrentWeatherData
    ) {
        Column(
            verticalAlignment = Alignment.CenterVertically,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = data.weatherDesc,
                modifier = GlanceModifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                style = TextStyle(
                    color = ColorProvider(Color.White),
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            )
            Spacer(modifier = GlanceModifier.height(12.dp))
            Image(
                provider = ImageProvider(
                    resId = data.weatherIconRes.toInt()
                ),
                contentDescription = "Weather icon",
                modifier = GlanceModifier
                    .width(60.dp)
                    .height(60.dp)
            )
            Spacer(modifier = GlanceModifier.height(12.dp))
            Text(
                text = "${data.temperatureCelsius}°C",
                modifier = GlanceModifier
                    .wrapContentWidth()
                    .wrapContentHeight(),
                style = TextStyle(
                    color = ColorProvider(Color.White),
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
            )
        }
    }

    @Composable
    private fun ListOfWeatherDetails(
        data: CurrentWeatherData
    ) {
        Column(
            verticalAlignment = Alignment.CenterVertically
        ) {
            WeatherDetails(
                value = data.pressure.roundToInt(),
                unit = " hpa",
                resID = R.drawable.ic_pressure,
                contentDescription = "Pressure",
                textStyle = TextStyle(color = ColorProvider(Color.White))
            )
            Spacer(modifier = GlanceModifier.height(12.dp))
            WeatherDetails(
                value = data.humidity.roundToInt(),
                unit = "%",
                resID = R.drawable.ic_drop,
                contentDescription = "Humidity",
                textStyle = TextStyle(color = ColorProvider(Color.White))
            )
            Spacer(modifier = GlanceModifier.height(12.dp))
            WeatherDetails(
                value = data.windSpeed.roundToInt(),
                unit = " km/h",
                resID = R.drawable.ic_wind,
                contentDescription = "Wind speed",
                textStyle = TextStyle(color = ColorProvider(Color.White))
            )
            Spacer(modifier = GlanceModifier.height(12.dp))
            WeatherDetails(
                unit = data.directionWindDesc,
                resID = data.directionWindIconRes.toInt(),
                contentDescription = "Wind direction",
                textStyle = TextStyle(color = ColorProvider(Color.White))
            )
        }
    }

    @Composable
    private fun WeatherDetails(
        value: Int? = null,
        unit: String = "",
        resID: Int,
        contentDescription: String? = null,
        textStyle: TextStyle = TextStyle(),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                provider = ImageProvider(resId = resID),
                contentDescription = contentDescription,
                modifier = GlanceModifier.size(25.dp)
            )
            Spacer(modifier = GlanceModifier.width(4.dp))
            if (value != null) {
                Text(
                    text = "$value",
                    style = textStyle
                )
            }
            Text(
                text = unit,
                style = textStyle
            )
        }
    }
}