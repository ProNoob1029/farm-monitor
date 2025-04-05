package com.techtornado.farmmonitor

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.techtornado.farmmonitor.data.Polygon

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    viewModel: MainViewModel = viewModel()
) {
    val data by viewModel.data.collectAsStateWithLifecycle()

    MainScreen(
        modifier = modifier,
        onGet = viewModel::getSoil,
        data = data
    )
}

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    onGet: () -> Unit,
    data: List<Polygon>
) {
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        Button(
            onClick = onGet
        ) {
            Text("GET")
        }
        Text(data.toString())
    }
}