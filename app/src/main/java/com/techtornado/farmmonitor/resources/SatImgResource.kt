@file:Suppress("PropertyName")

package com.techtornado.farmmonitor.resources

import com.techtornado.farmmonitor.BuildConfig
import io.ktor.resources.Resource

@Resource("/image/search")
data class SatImgResource(
    val appid: String = BuildConfig.WEATHER_API_KEY,
    val polygon_id: String,
    val end: Long = System.currentTimeMillis() / 1000,
    val start: Long = end - 60 * 60 * 24 * 30
)