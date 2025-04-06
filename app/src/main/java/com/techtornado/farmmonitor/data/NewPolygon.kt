package com.techtornado.farmmonitor.data

import kotlinx.serialization.Serializable

@Serializable
data class NewPolygon(
    val name: String,
    val geo_json: GeoJson
) {
    constructor(name: String, coordinates: List<List<Double>>): this(
        name,
        GeoJson(
            features = listOf(
                Feature(
                    geometry = Feature.Geometry(
                        coordinates = listOf(coordinates)
                    )
                )
            )
        )
    )

    @Serializable
    data class GeoJson(
        val type: String = "FeatureCollection",
        val features: List<Feature>
    )

    @Serializable
    data class Feature(
        val type: String = "Feature",
        val properties: Properties = Properties,
        val geometry: Geometry
    ) {
        @Serializable
        data class Geometry(
            val type: String = "Polygon",
            val coordinates: List<List<List<Double>>>
        )
    }

    @Serializable
    data object Properties
}