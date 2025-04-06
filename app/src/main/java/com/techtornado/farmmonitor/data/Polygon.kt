@file:Suppress("PropertyName")
@file:OptIn(ExperimentalSerializationApi::class)

package com.techtornado.farmmonitor.data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

@Serializable
data class Polygon(
    val id: String,
    val geo_json: GeoJson,
    val name: String,
    val center: List<Double>,
    val area: Double,
    val user_id: String
) {
    @Serializable
    data class GeoJson(
        val type: String = "Feature",
        val geometry: Geometry
    ) {
        @Serializable
        data class Geometry(
            val type: String = "Polygon",
            val coordinates: List<List<List<Double>>>
        )
    }
}