package com.techtornado.farmmonitor.data

import kotlinx.serialization.Serializable

@Serializable
data class NDVI(
    val dt: Long,
    val source: String,
    val zoom: Int,
    val dc: Int,
    val cl: Double,
    val data: Stat
)