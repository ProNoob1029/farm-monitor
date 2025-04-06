package com.techtornado.farmmonitor.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.DefaultMapProperties
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun CreateLandScreen(
    modifier: Modifier = Modifier,
    viewModel: CreateLandViewModel = viewModel()
) {
    CreateLandScreen(modifier, viewModel::uploadLand)
}

@Composable
fun CreateLandScreen(
    modifier: Modifier = Modifier,
    onDone: (name: String, polygon: List<LatLng>) -> Unit,
) {
    var name by rememberSaveable { mutableStateOf("") }
    val polygon = remember { mutableStateListOf<LatLng>() }

    Column(
        modifier = modifier.fillMaxSize()
            .padding(16.dp)
    ) {
        Text("Create plot", style = MaterialTheme.typography.headlineLarge)
        Spacer(Modifier.height(16.dp))
        Map(
            Modifier
                .aspectRatio(1f)
                .clip(shape = RoundedCornerShape(16.dp)),
            onMapClick = { polygon.add(it) },
            polygon = polygon.toList()
        )
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = {
                    if (polygon.isNotEmpty()) {
                        polygon.removeAt(polygon.lastIndex)
                    }
                },
            ) {
                Text("Undo")
            }
            Spacer(Modifier.weight(1f))
            Button(onClick = { onDone(name, polygon.toList()) }) {
                Text("Done")
            }
        }
        Spacer(Modifier.height(16.dp))
        TextField(
            value = name,
            label = { Text("Name") },
            onValueChange = { name = it }
        )
    }
}

@Composable
fun Map(
    modifier: Modifier = Modifier,
    onMapClick: (LatLng) -> Unit,
    polygon: List<LatLng>
) {
    val cluj = LatLng(46.77, 23.64)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cluj, 10f)
    }
    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = onMapClick,
        properties = DefaultMapProperties.copy(mapType = MapType.HYBRID)
    ) {
        Polygon(
            points = polygon,
            geodesic = true,
            fillColor = Color.Black.copy(alpha = 0.2f)
        )
    }
}