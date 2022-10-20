package com.example.weatherappdemo.di

import androidx.work.WorkManager
import com.example.weatherappdemo.data.location.DefaultLocationTracker
import com.example.weatherappdemo.data.remote.WidgetWorker
import com.example.weatherappdemo.data.repository.WeatherRepositoryImpl
import com.example.weatherappdemo.domain.location.LocationTracker
import com.example.weatherappdemo.domain.repository.WeatherRepository
import com.example.weatherappdemo.domain.util.httpClient
import com.example.weatherappdemo.domain.util.provideFusedLocationProviderClient
import com.example.weatherappdemo.domain.widget.PreferencesManager
import com.example.weatherappdemo.presentation.screens.WeatherViewModel
import com.example.weatherappdemo.presentation.widgets.WidgetViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val httpClientModule = module {
    single { httpClient }
}

val repositoryModule = module {
    single<WeatherRepository> { WeatherRepositoryImpl(get()) }
}

val viewModelModule = module {
    viewModel { WeatherViewModel(get(), get()) }
    viewModel { WidgetViewModel(get(), get(), get()) }
}

val preferencesManagerModule = module {
    single { PreferencesManager(androidApplication()) }
}

val workManagerModule = module {
    single { WorkManager.getInstance(androidApplication()) }
}

val fusedLocationProviderClientModule = module {
    single { provideFusedLocationProviderClient(androidApplication()) }
}

@ExperimentalCoroutinesApi
val bindLocationTrackerModule = module {
    single<LocationTracker> { DefaultLocationTracker(get(), get()) }
}

val widgetWorkerModule = module {
    worker { WidgetWorker(get(), get(), get()) }
}