package com.techtornado.farmmonitor.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.DefaultMapProperties
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun Map(
    modifier: Modifier = Modifier,
    latLng: LatLng,
    zoom: Float = 10f,
    onMapClick: (LatLng) -> Unit,
    polygon: List<LatLng>
) {
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(latLng, zoom)
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
            strokeColor = MaterialTheme.colorScheme.primary,
            fillColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.2f)
        )
    }
}