package com.techtornado.farmmonitor.resources

import com.techtornado.farmmonitor.APP_ID
import io.ktor.resources.Resource

@Resource("/ndvi/history")
data class NDVIResource(
    val appid: String = APP_ID,
) {
    @Resource("{id}")
    data class ById(
        val parent: NDVIResource = NDVIResource(),
        val id: String
    )
}