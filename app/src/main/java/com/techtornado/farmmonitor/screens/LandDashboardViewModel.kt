package com.techtornado.farmmonitor.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techtornado.farmmonitor.MonitorApplication
import com.techtornado.farmmonitor.UIState
import com.techtornado.farmmonitor.data.Forecast
import com.techtornado.farmmonitor.data.Land
import com.techtornado.farmmonitor.data.NDVI
import com.techtornado.farmmonitor.data.NdviHistory
import com.techtornado.farmmonitor.data.Polygon
import com.techtornado.farmmonitor.data.Soil
import com.techtornado.farmmonitor.data.Weather
import com.techtornado.farmmonitor.resources.CurrentWeatherResource
import com.techtornado.farmmonitor.resources.ForecastResource
import com.techtornado.farmmonitor.resources.NDVIHistoryResource
import com.techtornado.farmmonitor.resources.PolygonsResource
import com.techtornado.farmmonitor.resources.SoilResource
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LandDashboardViewModel: ViewModel() {
    val landState = MutableStateFlow<UIState<Land>>(UIState.Loading)
    private val httpClient = MonitorApplication.httpClient

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val land = fetchLand("67f0faf1fd068c7a6383561a")
                landState.update { UIState.Succes(land) }
            } catch (e: Exception) {
                Log.e("LandDashboardViewModel", e.toString())
            }
        }
    }

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
            currentNdvi = ndviHistory.sortedBy { it.dt }.last(),
            ndviHistory = ndviHistory,
            forecast = forecast
        )
    }
}