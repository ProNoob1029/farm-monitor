package com.techtornado.farmmonitor.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.techtornado.farmmonitor.AIViewModel
import com.techtornado.farmmonitor.MainViewModel
import com.techtornado.farmmonitor.data.Land

@Composable
fun AIPredictionsScreen(
    modifier: Modifier = Modifier,
    viewModel: AIViewModel = viewModel(),
    landid: String
) {
    /*LaunchedEffect(Unit) {
        viewModel.getAiResponse(land)
    }*/

    val aiResponse by viewModel.aiResponse.collectAsStateWithLifecycle()

    Column(modifier = modifier) {

        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box() {
                Text(
                    "AI Predictions",
                    modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineLarge,
                    fontStyle = FontStyle.Italic
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {

                Box(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Surface(
                        onClick = {viewModel.getAiResponse(landid)},
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier.height(100.dp)
                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "Get"
                            )
                        }
                    }
                }
                Box(
                    modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
                    contentAlignment = Alignment.Center
                )
                {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(16.dp),

                    ) {
                        Box(
                            modifier = Modifier.fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                aiResponse
                            )
                        }
                    }
                }

            }
        }
    }
}