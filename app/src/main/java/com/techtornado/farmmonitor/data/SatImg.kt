@file:OptIn(ExperimentalSerializationApi::class)

package com.techtornado.farmmonitor.data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@JsonIgnoreUnknownKeys
@Serializable
data class SatImg(
    val dt: Long,
    val stats: Stats
) {
    @JsonIgnoreUnknownKeys
    @Serializable
    data class Stats(
        val ndvi: String
    )
}