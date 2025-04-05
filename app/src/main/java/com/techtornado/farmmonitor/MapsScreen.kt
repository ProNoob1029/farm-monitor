package com.techtornado.farmmonitor

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
    polymaps: List<List<LatLng>> = emptyList()
) {
    val cluj = LatLng(46.77, 23.64)
    val clujMarkerState = rememberMarkerState(position = cluj)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(cluj, 10f)
    }
    GoogleMap(
        modifier = modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState
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
    }
}