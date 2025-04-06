package com.techtornado.farmmonitor.resources

import com.techtornado.farmmonitor.BuildConfig
import io.ktor.resources.Resource

@Resource("/soil/history")
data class SoilHistoryResource(
    val appid: String = BuildConfig.WEATHER_API_KEY,
    val polyid: String,
    val end: Long = System.currentTimeMillis() / 1000,
    val start: Long = end - 60 * 60 * 24 * 30
)