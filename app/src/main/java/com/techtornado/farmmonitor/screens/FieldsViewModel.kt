package com.techtornado.farmmonitor.screens

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.techtornado.farmmonitor.MonitorApplication
import com.techtornado.farmmonitor.UIState
import com.techtornado.farmmonitor.data.Polygon
import com.techtornado.farmmonitor.resources.PolygonsResource
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FieldsViewModel :ViewModel(){
    val state = MutableStateFlow<UIState<List<Polygon>>>(UIState.Loading)

    private val httpClient = MonitorApplication.httpClient

    init {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val lands = fetchLands()
                state.update {
                    UIState.Succes(lands)
                }
            } catch (e: Exception) {
                Log.e("FieldsViewModel", e.toString())
            }
        }
    }

    private suspend fun fetchLands(): List<Polygon> {
        val list: List<Polygon> = httpClient.get(PolygonsResource()).body()
        return list
    }
}