package com.example.weatherappdemo

import android.app.Application
import com.example.weatherappdemo.di.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.component.KoinComponent
import org.koin.core.context.startKoin

@ExperimentalCoroutinesApi
class WeatherApp: Application(), KoinComponent {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@WeatherApp)
            workManagerFactory()
            modules(
                repositoryModule,
                httpClientModule,
                viewModelModule,
                fusedLocationProviderClientModule,
                bindLocationTrackerModule,
                widgetWorkerModule,
                preferencesManagerModule,
                workManagerModule
            )
        }
    }
}