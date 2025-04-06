package com.techtornado.farmmonitor.resources

import com.techtornado.farmmonitor.BuildConfig
import io.ktor.resources.Resource

@Resource("/soil")
data class SoilResource(
    val appid: String = BuildConfig.WEATHER_API_KEY,
    val polyid: String
)