package com.techtornado.farmmonitor.data

import kotlinx.serialization.Serializable

@Serializable
data class Polygon(
    val id: String,
    val geoJson: GeoJson,
    val name: String,
    val center: Pair<Double, Double>,
    val area: Double,
    val userId: String
) {
    @Serializable
    data class GeoJson(
        val type: String,
        val geometry: Geometry
    ) {
        @Serializable
        data class Geometry(
            val type: String,
            val coordinates: List<Pair<Double, Double>>
        )
    }
}