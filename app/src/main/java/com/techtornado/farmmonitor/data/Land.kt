package com.techtornado.farmmonitor.data

import com.google.android.gms.maps.model.LatLng
import kotlinx.serialization.Serializable

@Serializable
data class Land(
    val polygon: Polygon,
    val currentWeather: Weather,
    val currentSoil: Soil,
    val currentNdvi: NDVI,
    val ndviHistory: NdviHistory,
    val forecast: Forecast
) {
    val location get() = polygon.center.toLatLng()
    val mapPolygon get() = polygon.geo_json.geometry.coordinates[0].toLatLng()
}