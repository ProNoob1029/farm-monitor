package com.techtornado.farmmonitor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techtornado.farmmonitor.data.Polygon
import com.techtornado.farmmonitor.data.Soil
import com.techtornado.farmmonitor.resources.PolygonsResource
import com.techtornado.farmmonitor.resources.SoilResource
import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.resources.Resources
import io.ktor.client.plugins.resources.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import kotlin.math.roundToInt

class MainViewModel: ViewModel() {
    val polygons = MutableStateFlow(emptyList<Polygon>())
    val soil = MutableStateFlow(Soil())

    val client = HttpClient(OkHttp) {
        install(Resources)
        defaultRequest {
            host = "api.agromonitoring.com/agro/1.0"
        }
    }

    fun getSoil() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val polyResponse = client.get(PolygonsResource()).bodyAsText()
                //Log.d("MainViewModel", responseString)
                val polys: List<Polygon> = Json.decodeFromString(polyResponse)
                polygons.update { polys }
                val soilResponse = client.get(SoilResource(polyid = polys[0].id)).bodyAsText()
                Log.d("MainViewModel", soilResponse)
                val kelvinSoil: Soil = Json.decodeFromString(soilResponse)
                soil.update {
                    kelvinSoil.copy(
                        t0 = ((kelvinSoil.t0 - 273.15) * 10).roundToInt() / 10.0,
                        t10 = ((kelvinSoil.t10 - 273.15) * 10).roundToInt() / 10.0,
                        moisture = (kelvinSoil.moisture * 100).roundToInt() / 100.0
                    )
                }
            } catch (e: Exception) {
                Log.e("MainViewModel", e.toString())
            }
        }
    }
}