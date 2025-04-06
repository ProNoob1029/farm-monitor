package com.techtornado.farmmonitor.resources

import com.techtornado.farmmonitor.BuildConfig
import io.ktor.resources.Resource

@Resource("/polygons")
data class PolygonsResource(
    val appid: String = BuildConfig.WEATHER_API_KEY,
) {
    @Resource("{id}")
    data class ById(
        val parent: PolygonsResource = PolygonsResource(),
        val id: String
    )
}