package com.techtornado.farmmonitor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techtornado.farmmonitor.data.Polygon
import com.techtornado.farmmonitor.resources.PolygonsResource
import io.ktor.client.HttpClient
import io.ktor.client.call.body
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

class MainViewModel: ViewModel() {
    val data = MutableStateFlow(emptyList<Polygon>())

    val client = HttpClient(OkHttp) {
        install(Resources)
        defaultRequest {
            host = "http://api.agromonitoring.com/agro/1.0"
        }
    }

    fun getSoil() {
        viewModelScope.launch(Dispatchers.IO) {
            val responseString = client.get(PolygonsResource()).bodyAsText()
            Log.d("MainViewModel", responseString)
            val polygons: List<Polygon> = Json.decodeFromString(responseString)
            data.update { polygons }
        }
    }
}