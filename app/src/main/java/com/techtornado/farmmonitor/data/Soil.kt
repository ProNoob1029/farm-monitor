package com.techtornado.farmmonitor.data

import kotlinx.serialization.Serializable

@Serializable
data class Soil(
    val dt: Long = 0,
    val t10: Double = 0.0,
    val moisture: Double = 0.0,
    val t0: Double = 0.0
)