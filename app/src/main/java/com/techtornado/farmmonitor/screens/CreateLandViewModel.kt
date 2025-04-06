package com.techtornado.farmmonitor.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.techtornado.farmmonitor.MonitorApplication
import com.techtornado.farmmonitor.data.NewPolygon
import com.techtornado.farmmonitor.resources.PolygonsResource
import io.ktor.client.plugins.resources.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class CreateLandViewModel: ViewModel() {
    private val httpClient = MonitorApplication.httpClient

    fun uploadLand(name: String, polygon: List<LatLng>) { viewModelScope.launch { try {
        val newPoly = NewPolygon(
            name = name,
            coordinates = (polygon + polygon.first()).map { listOf(it.longitude, it.latitude) }
        )
        Log.d("CreateViewModel", Json.encodeToString(newPoly))
        val response = httpClient.post(PolygonsResource()) {
            contentType(ContentType.Application.Json)
            setBody(newPoly)
        }
        Log.d("CreateViewModel", response.toString())
    } catch (e: Exception) {
        Log.e("CreateLandViewModel", e.toString())
    } } }
}