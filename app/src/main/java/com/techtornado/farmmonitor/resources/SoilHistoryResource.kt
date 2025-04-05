package com.techtornado.farmmonitor.resources

import com.techtornado.farmmonitor.APP_ID
import io.ktor.resources.Resource

@Resource("/soil/history")
data class SoilHistoryResource(
    val appid: String = APP_ID,
    val polyid: String,
    val end: Long = System.currentTimeMillis() / 1000,
    val start: Long = end - 60 * 60 * 24 * 30
)