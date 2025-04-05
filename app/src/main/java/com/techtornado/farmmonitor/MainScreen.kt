package com.techtornado.farmmonitor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng
import com.techtornado.farmmonitor.data.CurrentWeather
import com.techtornado.farmmonitor.data.Polygon
import com.techtornado.farmmonitor.data.Soil
import com.techtornado.farmmonitor.data.Stat

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    val data by viewModel.polygons.collectAsStateWithLifecycle()
    val soil by viewModel.soil.collectAsStateWithLifecycle()
    val stat by viewModel.stat.collectAsStateWithLifecycle()
    val weather by viewModel.weather.collectAsStateWithLifecycle()

    MainScreen(
        modifier = modifier,
        onGet = viewModel::getSoil,
        data = data,
        soil = soil,
        stat = stat,
        weather = weather
    )
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onGet: () -> Unit,
    data: List<Polygon>,
    soil: Soil,
    stat: Stat?,
    weather: CurrentWeather?
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
        ,
    ) {
        Button(
            onClick = onGet
        ) {
            Text("GET")
        }
        MapsScreen(
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp),
            polymaps = data.map { polygon ->
                polygon.geo_json.geometry.coordinates[0].map { points ->
                    LatLng(points[1], points[0])
                }
            }
        )
        Text(weather.toString())
    }
}