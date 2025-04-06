@file:OptIn(ExperimentalSerializationApi::class)

package com.techtornado.farmmonitor.data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
data class SatImg(
    val dt: Long,
    val stats: Stats
) {
    @Serializable
    data class Stats(
        val ndvi: String
    )
}