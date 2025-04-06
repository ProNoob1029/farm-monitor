package com.techtornado.farmmonitor.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.techtornado.farmmonitor.UIState
import com.techtornado.farmmonitor.data.Land

@Composable
fun LandDashboard(
    modifier: Modifier = Modifier,
    viewModel: LandDashboardViewModel = viewModel()
) {
    val landState by viewModel.landState.collectAsStateWithLifecycle()

    when (val land = landState) {
        is UIState.Error -> Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Womp womp", style = MaterialTheme.typography.headlineLarge.copy(color = MaterialTheme.colorScheme.error))
        }
        is UIState.Loading -> Loading(modifier.fillMaxSize())
        is UIState.Succes<Land> -> LandDashboard(modifier, land.result)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandDashboard(
    modifier: Modifier = Modifier,
    land: Land
) {
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(land.polygon.name)
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                ),
                scrollBehavior = topAppBarScrollBehavior,
                actions = {
                    IconButton(
                        onClick = {}
                    ) {
                        Icon(Icons.Default.Edit, "edit")
                    }
                }
            )
        },
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(topAppBarScrollBehavior.nestedScrollConnection)
    ) { scaffoldPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(scaffoldPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
        ) {
            Map(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp)),
                zoom = 15f,
                latLng = land.location,
                polygon = land.mapPolygon,
                onMapClick = {}
            )

            val spacerHeight = remember { 16.dp }

            Spacer(Modifier.height(spacerHeight))

            val surfaceModifiers = remember {
                Modifier.fillMaxWidth()
                    .height(50.dp)
            }

            SurfaceButton(
                onClick = {},
                modifier = surfaceModifiers
            ) {
                Text("Soil Stats")
            }
            Spacer(Modifier.height(spacerHeight))
            SurfaceButton(
                onClick = {},
                modifier = surfaceModifiers
            ) {
                Text("Weather Stats")
            }
            Spacer(Modifier.height(spacerHeight))
            SurfaceButton(
                onClick = {},
                modifier = surfaceModifiers
            ) {
                Text("Forecast Stats")
            }
            Spacer(Modifier.height(spacerHeight))
            SurfaceButton(
                onClick = {},
                modifier = surfaceModifiers
            ) {
                Text("AI Predictions")
            }
            Spacer(Modifier.height(spacerHeight))
            SurfaceButton(
                onClick = {},
                modifier = surfaceModifiers
            ) {
                Text("Edit & Delete")
            }
        }
    }
}

@Composable
fun SurfaceButton(onClick: () -> Unit, modifier: Modifier = Modifier, content: @Composable BoxScope.() -> Unit) {
    Surface(
        onClick = onClick,
        color = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(16.dp)
    ) {
        Box(modifier = modifier) {
            content()
        }
    }
}

@Composable
fun Loading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}