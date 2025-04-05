package com.techtornado.farmmonitor.data

import kotlinx.serialization.Serializable

@Serializable
data class Stat(
    val std: Double,
    val p75: Double,
    val min: Double,
    val max: Double,
    val median: Double,
    val p25: Double,
    val num: Int,
    val mean: Double
)