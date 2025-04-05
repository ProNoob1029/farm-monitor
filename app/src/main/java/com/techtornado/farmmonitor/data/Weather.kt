@file:OptIn(ExperimentalSerializationApi::class)

package com.techtornado.farmmonitor.data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonIgnoreUnknownKeys

@JsonIgnoreUnknownKeys
@Serializable
data class Weather(
    val dt: Long,
    val weather: List<Precipitation>,
    val main: Main,
    val wind: Wind,
    val clouds: Clouds,
    val rain: Rain = Rain(),
    val snow: Snow = Snow(),
) {
    @JsonIgnoreUnknownKeys
    @Serializable
    data class Precipitation(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String
    )

    @JsonIgnoreUnknownKeys
    @Serializable
    data class Main(
        val temp: Double,
        val feels_like: Double,
        val temp_min: Double,
        val temp_max: Double,
        val pressure: Int,
        val humidity: Int,
        val sea_level: Int,
        val grnd_level: Int,
        val temp_kf: Double = 0.0
    )

    @JsonIgnoreUnknownKeys
    @Serializable
    data class Wind(
        val speed: Double,
        val deg: Int,
        val gust: Double = 0.0
    )

    @JsonIgnoreUnknownKeys
    @Serializable
    data class Clouds(
        val all: Int
    )

    @JsonIgnoreUnknownKeys
    @Serializable
    data class Rain(
        val `3h`: Double? = null
    )

    @JsonIgnoreUnknownKeys
    @Serializable
    data class Snow(
        val `3h`: Double? = null
    )
}