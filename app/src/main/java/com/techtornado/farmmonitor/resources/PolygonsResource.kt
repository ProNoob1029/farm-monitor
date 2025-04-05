package com.techtornado.farmmonitor.resources

import com.techtornado.farmmonitor.APP_ID
import io.ktor.resources.Resource

@Resource("/polygons")
data class PolygonsResource(
    val appid: String = APP_ID,
) {
    @Resource("{id}")
    data class ById(
        val parent: PolygonsResource = PolygonsResource(),
        val id: String
    )
}