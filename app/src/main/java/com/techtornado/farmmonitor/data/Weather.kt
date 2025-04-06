@file:OptIn(ExperimentalSerializationApi::class)

package com.techtornado.farmmonitor.data

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable

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
    @Serializable
    data class Precipitation(
        val id: Int,
        val main: String,
        val description: String,
        val icon: String
    )

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

    @Serializable
    data class Wind(
        val speed: Double,
        val deg: Int,
        val gust: Double = 0.0
    )

    @Serializable
    data class Clouds(
        val all: Int
    )

    @Serializable
    data class Rain(
        val `3h`: Double? = null
    )

    @Serializable
    data class Snow(
        val `3h`: Double? = null
    )
}