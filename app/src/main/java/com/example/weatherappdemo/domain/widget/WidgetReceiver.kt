package com.example.weatherappdemo.domain.widget

import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.GlanceAppWidgetReceiver
import com.example.weatherappdemo.presentation.widgets.WeatherWidget

class WidgetReceiver: GlanceAppWidgetReceiver()  {
    override val glanceAppWidget: GlanceAppWidget
        get() = WeatherWidget()
}