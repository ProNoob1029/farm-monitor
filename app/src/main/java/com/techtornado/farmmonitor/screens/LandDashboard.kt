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
import androidx.compose.runtime.LaunchedEffect
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
import kotlin.math.roundToInt

@Composable
fun LandDashboard(
    modifier: Modifier = Modifier,
    viewModel: LandDashboardViewModel = viewModel(),
    navToAi: (String) -> Unit,
    landId: String
) {
    LaunchedEffect(Unit) {
        viewModel.loadData(landId)
    }
    val landState by viewModel.landState.collectAsStateWithLifecycle()

    when (val land = landState) {
        is UIState.Error -> Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Womp womp", style = MaterialTheme.typography.headlineLarge.copy(color = MaterialTheme.colorScheme.error))
        }
        is UIState.Loading -> Loading(modifier.fillMaxSize())
        is UIState.Succes<Land> -> LandDashboard(modifier, land = land.result, navToAi = navToAi)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LandDashboard(
    modifier: Modifier = Modifier,
    navToAi: (String) -> Unit,
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
                .padding(horizontal = 16.dp)
                .padding()
                .verticalScroll(rememberScrollState()),
        ) {

            val spacerHeight = remember { 16.dp }

            Spacer(Modifier.height(spacerHeight))

            Map(
                modifier = Modifier
                    .aspectRatio(1f)
                    .clip(RoundedCornerShape(16.dp)),
                zoom = 15f,
                latLng = land.location,
                polygon = land.mapPolygon,
                onMapClick = {}
            )

            Spacer(Modifier.height(spacerHeight))

            val surfaceModifiers = remember {
                Modifier.fillMaxWidth()
                    .height(80.dp)
            }

            SurfaceButton(
                onClick = {},
                modifier = surfaceModifiers
            ) {
                Text("Soil Stats, moisture: ${land.currentSoil.moisture}")
            }
            Spacer(Modifier.height(spacerHeight))
            SurfaceButton(
                onClick = {},
                modifier = surfaceModifiers
            ) {
                Text("Weather Stats, temp: ${((land.currentWeather.main.temp - 273.15) * 100).roundToInt() / 100.0 }")
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
                onClick = { navToAi(land.polygon.id) },
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
            Spacer(Modifier.height(spacerHeight))
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
        Box(modifier = modifier, contentAlignment = Alignment.Center) {
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