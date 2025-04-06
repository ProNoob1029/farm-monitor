package com.techtornado.farmmonitor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.type.content
import com.techtornado.farmmonitor.data.Forecast
import com.techtornado.farmmonitor.data.NDVI
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

class MainViewModel: ViewModel() {
    val polygons = MutableStateFlow(emptyList<Polygon>())
    val soil = MutableStateFlow(Soil())
    val stat = MutableStateFlow<Stat?>(null)
    val weather = MutableStateFlow<Weather?>(null)
    val forecast = MutableStateFlow<Forecast>(emptyList())
    val aiResponse = MutableStateFlow("")

    val client = MonitorApplication.httpClient
    val aiModel = MonitorApplication.aiModel

    fun getAiResponse() { viewModelScope.launch { try {
        val plots: List<Polygon> = client.get(PolygonsResource()).body()
        Log.i("MainViewModel", "plots fetched successfully")
        val plot = plots[0]
        val ndviHistory: List<NDVI> = client.get(NDVIHistoryResource(polygon_id = plot.id)).body()
        Log.i("MainViewModel", "ndvi fetched successfully")
        val currentWeather: Weather = client.get(CurrentWeatherResource(lat = plot.center[1], lon = plot.center[0])).body()
        Log.i("MainViewModel", "weather fetched successfully")
        Log.i("MainViewModel", "current date: ${currentWeather.dt.convertTimestampToReadableFormat()}")
        val forecast: Forecast = client.get(ForecastResource(lat = plot.center[1], lon = plot.center[0])).body()
        Log.i("MainViewModel", "forecast fetched successfully")
        val soil: Soil = client.get(SoilResource(polyid = plot.id)).body()
        Log.i("MainViewModel", "soil fetched successfully")

        val response = aiModel.generateContent(
            content {
                text("""
                        PLOT INFO
                        plot name: ${plot.name}
                        plot latitude: ${plot.center[1]}
                        plot longitude: ${plot.center[0]}
                        plot area: ${plot.area}
                        """.trimIndent())
                text("NDVI HISTORY")
                for (ndvi in ndviHistory) {
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
                        timestamp: ${currentWeather.dt.convertTimestampToReadableFormat()}
                        description: ${currentWeather.weather[0].description}
                        temperature celsius: ${currentWeather.main.temp - 273.15}
                        feels like temp celsius: ${currentWeather.main.feels_like - 273.15}
                        min temp celsius: ${currentWeather.main.temp_min - 273.15}
                        max temp celsius: ${currentWeather.main.temp_max - 273.15}
                        pressure: ${currentWeather.main.pressure}
                        humidity: ${currentWeather.main.humidity}
                        wind speed meter/sec: ${currentWeather.wind.speed}
                        wind direction degrees: ${currentWeather.wind.deg}
                        wind gust: ${currentWeather.wind.gust}
                        cloudiness percentage: ${currentWeather.clouds.all}
                        rain volume in last 3 hours (ignore if null): ${currentWeather.rain.`3h`}
                        snow volume in last 3 hours (ignore if null): ${currentWeather.snow.`3h`}
                    """.trimIndent())
                text("FORECAST")
                for (weather in forecast) {
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
                        timestamp: ${soil.dt.convertTimestampToReadableFormat()}
                        moisture: ${soil.moisture}
                        surface temperature celsius: ${soil.t0 - 273.15}
                        temperature at 10 centimeters depth celsius: ${soil.t10 - 273.15}
                    """.trimIndent())
            }
        ).text

        aiResponse.update {
            response ?: ""
        }
    } catch (e: Exception) {
        Log.e("MainViewModel", e.toString())
    } } }

    fun getSoil() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val polyResponse = client.get(PolygonsResource()).bodyAsText()
                //Log.d("MainViewModel", responseString)
                val polys: List<Polygon> = Json.decodeFromString(polyResponse)
                polygons.update { polys }
                val soilResponse = client.get(SoilResource(polyid = polys[0].id)).bodyAsText()
                //Log.d("MainViewModel", soilResponse)
                val kelvinSoil: Soil = Json.decodeFromString(soilResponse)
                soil.update {
                    kelvinSoil.copy(
                        t0 = ((kelvinSoil.t0 - 273.15) * 10).roundToInt() / 10.0,
                        t10 = ((kelvinSoil.t10 - 273.15) * 10).roundToInt() / 10.0,
                        moisture = (kelvinSoil.moisture * 100).roundToInt() / 100.0
                    )
                }
                val satImgResponse = client.get(SatImgResource(polygon_id = polys[0].id)).bodyAsText()
                Log.d("MainViewModel", satImgResponse)
                val satImgs: List<SatImg> = Json.decodeFromString(satImgResponse)
                val newestImg = satImgs.maxBy { it.dt }
                val ndviResource = client.get(newestImg.stats.ndvi).bodyAsText()
                Log.d("MainViewModel", ndviResource)
                val ndviStat: Stat = Json.decodeFromString(ndviResource)
                stat.update {
                    ndviStat
                }
                val weatherResponse = client.get(CurrentWeatherResource(lat = polys[0].center[1], lon = polys[0].center[0])).bodyAsText()
                Log.d("MainViewModel", weatherResponse)
                val currWeather: Weather = Json.decodeFromString(weatherResponse)
                weather.update {
                    currWeather
                }
                val forecastResponse = client.get(ForecastResource(lat = polys[0].center[1], lon = polys[0].center[0])).bodyAsText()
                Log.d("MainViewModel", forecastResponse)
                val weatherForecast: Forecast = Json.decodeFromString(forecastResponse)
                forecast.update {
                    weatherForecast
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", e.toString())
            }
        }
    }

    fun Long.convertTimestampToReadableFormat(): String {
        val sdf = SimpleDateFormat("yyyy-MMM-dd HH:mm:ss", Locale.getDefault())
        val date = Date(this * 1000)
        return sdf.format(date)
    }
}