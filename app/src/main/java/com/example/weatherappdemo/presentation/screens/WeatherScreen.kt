package com.example.weatherappdemo.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt
import com.example.weatherappdemo.R
import com.example.weatherappdemo.domain.weather.daily.WeatherDailyData
import com.example.weatherappdemo.domain.weather.hourly.WeatherHourlyData
import com.example.weatherappdemo.presentation.ui.theme.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.util.*

@Composable
fun WeatherScreen(
    modifier: Modifier = Modifier,
    viewModel: WeatherViewModel
) {
    val background = Brush.verticalGradient(
        colors = listOf(
            SandyBrown, LightPink, MediumPurple, MidnightBlue
        )
    )
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if(viewModel.state.isLoading) {
            CircularProgressIndicator(
                color = Color.White
            )
        } else {
            Content(
                modifier = modifier,
                viewModel = viewModel
            )
        }
        viewModel.state.error?.let { error ->
            Text(
                text = error,
                color = Color.White,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Composable
private fun Content(
    modifier: Modifier,
    viewModel: WeatherViewModel
) {
    Text(
        text = "Today: ${
            SimpleDateFormat("dd.MM", Locale.getDefault()).format(Date())
        }",
        modifier = modifier
            .padding(top = dimensionResource(R.dimen.dip_6)),
        fontSize = 14.sp,
        style = MaterialTheme.typography.h3
    )
    Spacer(modifier = modifier.height(dimensionResource(R.dimen.dip_10)))
    TodayWeather(
        modifier = modifier,
        viewModel = viewModel
    )
    Spacer(modifier = modifier.height(dimensionResource(R.dimen.dip_16)))
    Text(
        text = stringResource(R.string.hourly),
        style = MaterialTheme.typography.h3
    )
    Spacer(modifier = modifier.height(dimensionResource(R.dimen.dip_16)))
    HourlyWeather(
        modifier = modifier,
        viewModel = viewModel
    )
    Spacer(modifier = modifier.height(dimensionResource(R.dimen.dip_16)))
    Text(
        text = stringResource(R.string.daily),
        style = MaterialTheme.typography.h3
    )
    Spacer(modifier = modifier.height(dimensionResource(R.dimen.dip_16)))
    DailyWeather(
        modifier = modifier,
        viewModel = viewModel
    )
}

@Composable
private fun TodayWeather(
    modifier: Modifier,
    viewModel: WeatherViewModel
) {
    viewModel.state.weatherHourlyInfo?.currentWeatherData?.let { data ->
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = modifier
                .fillMaxWidth()
        ) {
            CurrentWeather(
                modifier = modifier,
                data = data
            )
            ListOfWeatherDetails(
                modifier = modifier,
                data = data
            )
        }
    }
}

@Composable
private fun CurrentWeather(
    modifier: Modifier,
    data: WeatherHourlyData
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = data.weatherType.weatherDesc,
            style = MaterialTheme.typography.h5
        )
        Spacer(modifier = modifier.height(dimensionResource(R.dimen.dip_12)))
        Image(
            painter = painterResource(id = data.weatherType.iconRes),
            contentDescription = stringResource(R.string.weather_icon),
            modifier = modifier.width(dimensionResource(R.dimen.dip_60))
        )
        Spacer(modifier = modifier.height(dimensionResource(R.dimen.dip_12)))
        Text(
            text = "${data.temperatureCelsius}°C",
            style = MaterialTheme.typography.h1
        )
    }
}

@Composable
private fun ListOfWeatherDetails(
    modifier: Modifier,
    data: WeatherHourlyData
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        WeatherDetails(
            modifier = modifier,
            value = data.pressure.roundToInt(),
            unit = " hpa",
            icon = ImageVector.vectorResource(id = R.drawable.ic_pressure),
            contentDescription = stringResource(R.string.pressure)
        )
        Spacer(modifier = modifier.height(dimensionResource(R.dimen.dip_12)))
        WeatherDetails(
            modifier = modifier,
            value = data.humidity.roundToInt(),
            unit = "%",
            icon = ImageVector.vectorResource(id = R.drawable.ic_drop),
            contentDescription = stringResource(R.string.humidity)
        )
        Spacer(modifier = modifier.height(dimensionResource(R.dimen.dip_12)))
        WeatherDetails(
            modifier = modifier,
            value = data.windSpeed.roundToInt(),
            unit = " km/h",
            icon = ImageVector.vectorResource(id = R.drawable.ic_wind),
            contentDescription = stringResource(R.string.wind_speed)
        )
        Spacer(modifier = modifier.height(dimensionResource(R.dimen.dip_12)))
        WeatherDetails(
            modifier = modifier,
            unit = data.windDirectionType.directionDesc,
            icon = ImageVector.vectorResource(id = data.windDirectionType.iconRes),
            contentDescription = stringResource(R.string.wind_direction)
        )
    }
}

@Composable
private fun WeatherDetails(
    modifier: Modifier,
    value: Int? = null,
    unit: String = "",
    icon: ImageVector,
    contentDescription: String? = null,
    iconTint: Color = Color.White
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = iconTint,
            modifier = modifier.size(dimensionResource(R.dimen.dip_25))
        )
        Spacer(modifier = modifier.width(dimensionResource(R.dimen.dip_4)))
        if (value != null) {
            Text(
                text = "$value",
                style = MaterialTheme.typography.body1
            )
        }
        Text(
            text = unit,
            style = MaterialTheme.typography.body1
        )
    }
}

@Composable
private fun HourlyWeather(
    modifier: Modifier,
    viewModel: WeatherViewModel
) {
    viewModel.state.weatherHourlyInfo?.hourlyWeatherData?.get(0)?.let { data ->
        LazyRow(content = {
            items(data) { weatherData ->
                HourlyWeatherItem(
                    modifier = modifier,
                    weatherData = weatherData
                )
            }
        })
    }
}

@Composable
private fun HourlyWeatherItem(
    modifier: Modifier,
    weatherData: WeatherHourlyData
) {
    Column(
        modifier = modifier
            .height(dimensionResource(R.dimen.dip_100))
            .padding(horizontal = dimensionResource(R.dimen.dip_16)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = weatherData.time.format(
                DateTimeFormatter.ofPattern("HH:mm")
            ),
            style = MaterialTheme.typography.body1
        )
        Image(
            painter = painterResource(id = weatherData.weatherType.iconRes),
            contentDescription = null,
            modifier = modifier.width(dimensionResource(R.dimen.dip_40))
        )
        Text(
            text = "${weatherData.temperatureCelsius}°C",
            style = MaterialTheme.typography.h6
        )
    }
}

@Composable
private fun DailyWeather(
    modifier: Modifier,
    viewModel: WeatherViewModel
) {
    viewModel.state.weatherDailyInfo?.dailyWeatherData?.get(0)?.let { data ->
        LazyColumn(
            content = {
                items(data) { weatherData ->
                    DailyWeatherItem(
                        modifier = modifier,
                        weatherData = weatherData
                    )
                }
            }
        )
    }
}

@Composable
private fun DailyWeatherItem(
    modifier: Modifier,
    weatherData: WeatherDailyData
) {
    ConstraintLayout(
        modifier = modifier
            .padding(
                start = dimensionResource(R.dimen.dip_16),
                end = dimensionResource(R.dimen.dip_16)
            )
            .fillMaxWidth()
    ) {
        val (date, sunrise, sunset, temperature, precipitation,
            iconWeather, iconSunrise, iconSunset,
            iconThermometer, iconPrecipitation
        ) = createRefs()
        Text(
            text = weatherData.date.format(
                DateTimeFormatter.ofPattern("dd.MM")
            ),
            style = MaterialTheme.typography.h2,
            modifier = modifier
                .constrainAs(date) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                }
        )
        Image(
            painter = painterResource(id = weatherData.weatherType.iconRes),
            contentDescription = stringResource(R.string.weather_icon),
            modifier = modifier
                .width(40.dp)
                .constrainAs(iconWeather) {
                    top.linkTo(date.bottom, 18.dp)
                    start.linkTo(date.start)
                    end.linkTo(date.end)
                    bottom.linkTo(parent.bottom)
                }
        )
        Image(
            painter = painterResource(id = R.drawable.ic_sunrise),
            contentDescription = stringResource(R.string.sunrise_icon),
            modifier = modifier
                .width(30.dp)
                .height(30.dp)
                .constrainAs(iconSunrise) {
                    top.linkTo(parent.top)
                    start.linkTo(date.end, 40.dp)
                }
        )
        Text(
            text = LocalDateTime.parse(weatherData.sunrise).format(
                DateTimeFormatter.ofPattern("HH:mm")
            ),
            color = Color.LightGray,
            style = MaterialTheme.typography.body1,
            modifier = Modifier
                .constrainAs(sunrise) {
                    top.linkTo(parent.top)
                    start.linkTo(iconSunrise.end, 8.dp)
                    bottom.linkTo(iconSunrise.bottom)
                }
        )
        Image(
            painterResource(R.drawable.ic_sunset),
            contentDescription = stringResource(R.string.sunset_icon),
            modifier = modifier
                .width(30.dp)
                .height(30.dp)
                .constrainAs(iconSunset) {
                    start.linkTo(iconSunrise.start)
                    bottom.linkTo(iconWeather.bottom)
                }
        )
        Text(
            text = LocalDateTime.parse(weatherData.sunset).format(
                DateTimeFormatter.ofPattern("HH:mm")
            ),
            color = Color.LightGray,
            style = MaterialTheme.typography.body1,
            modifier = modifier
                .constrainAs(sunset) {
                    top.linkTo(iconSunset.top)
                    start.linkTo(sunrise.start)
                    bottom.linkTo(iconSunset.bottom)
                }
        )
        Image(
            painter = painterResource(id = R.drawable.ic_thermometer),
            contentDescription = stringResource(R.string.thermometer_icon),
            modifier = modifier
                .width(40.dp)
                .constrainAs(iconThermometer) {
                    top.linkTo(parent.top)
                    start.linkTo(sunrise.end, 20.dp)
                    end.linkTo(temperature.start)
                    bottom.linkTo(parent.bottom)
                }
        )
        Text(
            text = "${weatherData.temperatureCelsius_min.toInt()}°C — " +
                    "${weatherData.temperatureCelsius_max.toInt()}°C",
            style = MaterialTheme.typography.h4,
            modifier = modifier
                .constrainAs(temperature) {
                    top.linkTo(parent.top)
                    start.linkTo(iconThermometer.end)
                    end.linkTo(iconPrecipitation.start, 20.dp)
                    bottom.linkTo(parent.bottom)
                }
        )
        Text(
            text = "${weatherData.precipitation_sum} mm",
            style = MaterialTheme.typography.caption,
            modifier = modifier
                .constrainAs(precipitation) {
                    top.linkTo(parent.top)
                    start.linkTo(iconPrecipitation.start)
                    end.linkTo(iconPrecipitation.end)
                    bottom.linkTo(iconPrecipitation.top)
                }
        )
        Image(
            painter = painterResource(id = R.drawable.ic_precipitation),
            contentDescription = stringResource(R.string.precipitation_icon),
            modifier = Modifier
                .width(40.dp)
                .constrainAs(iconPrecipitation) {
                    top.linkTo(iconThermometer.top)
                    bottom.linkTo(iconThermometer.bottom)
                    end.linkTo(parent.end)
                }
        )
    }
    Spacer(modifier = Modifier.height(dimensionResource(R.dimen.dip_24)))
}