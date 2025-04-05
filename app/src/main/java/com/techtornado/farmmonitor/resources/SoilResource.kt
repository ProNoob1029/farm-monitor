package com.techtornado.farmmonitor.resources

import com.techtornado.farmmonitor.APP_ID
import io.ktor.resources.Resource

@Resource("/soil")
data class SoilResource(
    val appid: String = APP_ID,
    val polyid: String
)