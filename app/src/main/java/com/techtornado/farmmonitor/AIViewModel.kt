package com.techtornado.farmmonitor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.type.content
import com.techtornado.farmmonitor.data.Forecast
import com.techtornado.farmmonitor.data.Land
import com.techtornado.farmmonitor.data.NDVI
import com.techtornado.farmmonitor.data.NdviHistory
import com.techtornado.farmmonitor.data.Weather
import com.techtornado.farmmonitor.data.Polygon
import com.techtornado.farmmonitor.data.SatImg
import com.techtornado.farmmonitor.data.Soil
import com.techtornado.farmmonitor.data.Stat
import com.techtornado.farmmonitor.resources.CurrentWeatherResource
import com.techtornado.farmmonitor.resources.ForecastResource
import com.techtornado.farmmonitor.resources.NDVIHistoryResource
import com.techtornado.farmmonitor.resources.PolygonsResource
import com.techtornado.farmmonitor.resources.SatImgResource
import com.techtornado.farmmonitor.resources.SoilResource
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.roundToInt

class AIViewModel: ViewModel() {
    val aiResponse = MutableStateFlow("")
    val aiModel = MonitorApplication.aiModel
    private val httpClient = MonitorApplication.httpClient

    private suspend fun fetchLand(id: String): Land {
        val polygon: Polygon = httpClient.get(PolygonsResource.ById(id = id)).body()
        Log.i("MainViewModel", "polygon fetched successfully")
        val ndviHistory: NdviHistory = httpClient.get(NDVIHistoryResource(polygon_id = polygon.id)).body()
        Log.i("MainViewModel", "ndvi fetched successfully")
        val currentWeather: Weather = httpClient.get(CurrentWeatherResource(lat = polygon.center[1], lon = polygon.center[0])).body()
        Log.i("MainViewModel", "weather fetched successfully")
        val forecast: Forecast = httpClient.get(ForecastResource(lat = polygon.center[1], lon = polygon.center[0])).body()
        Log.i("MainViewModel", "forecast fetched successfully")
        val soil: Soil = httpClient.get(SoilResource(polyid = polygon.id)).body()
        Log.i("MainViewModel", "soil fetched successfully")
        return Land(
            polygon = polygon,
            currentWeather = currentWeather,
            currentSoil = soil,
            currentNdvi = ndviHistory.sortedBy { it.dt }.lastOrNull(),
            ndviHistory = ndviHistory,
            forecast = forecast
        )
    }

    fun getAiResponse(landId: String) { viewModelScope.launch { try {
        val land = fetchLand(landId)

        val response = aiModel.generateContent(
            content {
                text("""
                        PLOT INFO
                        plot name: ${land.polygon.name}
                        plot latitude: ${land.polygon.center[1]}
                        plot longitude: ${land.polygon.center[0]}
                        plot area: ${land.polygon.area}
                        """.trimIndent())
                text("NDVI HISTORY")
                for (ndvi in land.ndviHistory) {
                    text("""
                        NDVI DATAPOINT
                        timestamp: ${ndvi.dt.convertTimestampToReadableFormat()}
                        standard deviation: ${ndvi.data.std}
                        first quartile: ${ndvi.data.p25}
                        min index: ${ndvi.data.min}
                        max index: ${ndvi.data.max}
                        median index: ${ndvi.data.median}
                        third quartile: ${ndvi.data.p75}
                        mean index: ${ndvi.data.mean}
                    """.trimIndent())
                }
                text("""
                        CURRENT WEATHER
                        timestamp: ${land.currentWeather.dt.convertTimestampToReadableFormat()}
                        description: ${land.currentWeather.weather[0].description}
                        temperature celsius: ${land.currentWeather.main.temp - 273.15}
                        feels like temp celsius: ${land.currentWeather.main.feels_like - 273.15}
                        min temp celsius: ${land.currentWeather.main.temp_min - 273.15}
                        max temp celsius: ${land.currentWeather.main.temp_max - 273.15}
                        pressure: ${land.currentWeather.main.pressure}
                        humidity: ${land.currentWeather.main.humidity}
                        wind speed meter/sec: ${land.currentWeather.wind.speed}
                        wind direction degrees: ${land.currentWeather.wind.deg}
                        wind gust: ${land.currentWeather.wind.gust}
                        cloudiness percentage: ${land.currentWeather.clouds.all}
                        rain volume in last 3 hours (ignore if null): ${land.currentWeather.rain.`3h`}
                        snow volume in last 3 hours (ignore if null): ${land.currentWeather.snow.`3h`}
                    """.trimIndent())
                text("FORECAST")
                for (weather in land.forecast) {
                    text("""
                        FORECAST DATAPOINT
                        timestamp: ${weather.dt.convertTimestampToReadableFormat()}
                        description: ${weather.weather[0].description}
                        temperature celsius: ${weather.main.temp - 273.15}
                        feels like temp celsius: ${weather.main.feels_like - 273.15}
                        min temp celsius: ${weather.main.temp_min - 273.15}
                        max temp celsius: ${weather.main.temp_max - 273.15}
                        pressure: ${weather.main.pressure}
                        humidity: ${weather.main.humidity}
                        wind speed meter/sec: ${weather.wind.speed}
                        wind direction degrees: ${weather.wind.deg}
                        wind gust: ${weather.wind.gust}
                        cloudiness percentage: ${weather.clouds.all}
                        rain volume in last 3 hours (ignore if null): ${weather.rain.`3h`}
                        snow volume in last 3 hours (ignore if null): ${weather.snow.`3h`}
                    """.trimIndent())
                }
                text("""
                        CURRENT SOIL DATA
                        timestamp: ${land.currentSoil.dt.convertTimestampToReadableFormat()}
                        moisture: ${land.currentSoil.moisture}
                        surface temperature celsius: ${land.currentSoil.t0 - 273.15}
                        temperature at 10 centimeters depth celsius: ${land.currentSoil.t10 - 273.15}
                    """.trimIndent())
            }
        ).text

        aiResponse.update {
            response ?: ""
        }
    } catch (e: Exception) {
        Log.e("MainViewModel", e.toString())
    } } }

    fun Long.convertTimestampToReadableFormat(): String {
        val sdf = SimpleDateFormat("yyyy-MMM-dd HH:mm:ss", Locale.getDefault())
        val date = Date(this * 1000)
        return sdf.format(date)
    }
}