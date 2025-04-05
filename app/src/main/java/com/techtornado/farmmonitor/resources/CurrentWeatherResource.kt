package com.techtornado.farmmonitor.resources

import com.techtornado.farmmonitor.APP_ID
import io.ktor.resources.Resource

@Resource("/weather")
class CurrentWeatherResource(
    val appid: String = APP_ID,
    val lat: Double,
    val lon: Double
)