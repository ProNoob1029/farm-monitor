package com.techtornado.farmmonitor.resources

import com.techtornado.farmmonitor.BuildConfig
import io.ktor.resources.Resource

@Resource("/weather")
class CurrentWeatherResource(
    val appid: String = BuildConfig.WEATHER_API_KEY,
    val lat: Double,
    val lon: Double
)