package com.example.weatherappdemo.presentation

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.example.weatherappdemo.presentation.screens.WeatherScreen
import com.example.weatherappdemo.presentation.screens.WeatherViewModel
import com.example.weatherappdemo.presentation.ui.theme.*
import com.example.weatherappdemo.presentation.widgets.WidgetViewModel
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by inject()
    private val viewModelWidget: WidgetViewModel by inject()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeatherHourlyInfo()
            viewModel.loadWeatherDailyInfo()
        }
        permissionLauncher.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        ))
        setContent {
            viewModelWidget.setCategory()
            WeatherAppDemoTheme {
                WeatherScreen(
                    viewModel = viewModel
                )
            }
        }
    }
}