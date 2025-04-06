package com.techtornado.farmmonitor.resources

import com.techtornado.farmmonitor.BuildConfig
import io.ktor.resources.Resource

@Resource("/weather/history")
class WeatherHistoryResource(
    val appid: String = BuildConfig.WEATHER_API_KEY,
    val lat: Double,
    val lon: Double,
    val end: Long = System.currentTimeMillis() / 1000,
    val start: Long = end - 60 * 60 * 24 * 30
)