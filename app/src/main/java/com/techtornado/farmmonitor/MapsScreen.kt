package com.techtornado.farmmonitor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.Polygon
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun MapsScreen(
    modifier: Modifier = Modifier,
    polymaps: List<List<LatLng>> = emptyList(),
    onMapClick: (LatLng) -> Unit,
    polygon: List<LatLng>
) {
    val cluj = LatLng(46.77, 23.64)
    val clujMarkerState = rememberMarkerState(position = cluj)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cluj, 10f)
    }
    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        onMapClick = onMapClick
    ) {

        Marker(
            state = clujMarkerState,
            title = "Cluj",
            snippet = "Marker in Cluj"
        )

        for (points in polymaps) {
            Polygon(
                points = points
            )
        }

        Polygon(
            points = polygon,
            geodesic = true,
            fillColor = Color.Black.copy(alpha = 0.2f)
        )
    }
}