@file:Suppress("PropertyName")

package com.techtornado.farmmonitor.resources

import com.techtornado.farmmonitor.APP_ID
import io.ktor.resources.Resource

@Resource("/image/search")
data class SatImgResource(
    val appid: String = APP_ID,
    val polygon_id: String,
    val end: Long = System.currentTimeMillis() / 1000,
    val start: Long = end - 60 * 60 * 24 * 30
)