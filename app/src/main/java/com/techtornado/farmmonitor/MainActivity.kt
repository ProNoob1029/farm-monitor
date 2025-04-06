package com.techtornado.farmmonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.techtornado.farmmonitor.data.Land
import com.techtornado.farmmonitor.screens.AIPredictionsScreen
import com.techtornado.farmmonitor.screens.FieldsScreen
import com.techtornado.farmmonitor.screens.ForecastScreen
import com.techtornado.farmmonitor.screens.LandDashboardViewModel
import com.techtornado.farmmonitor.screens.SoilScreen
import com.techtornado.farmmonitor.screens.WeatherScreen
import com.techtornado.farmmonitor.ui.theme.FarmMonitorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FarmMonitorTheme {
                Scaffold { padding ->
                    /*MainScreen(
                        modifier = Modifier.padding(padding)
                    )*/
                    /*CreateLandScreen(
                        modifier = Modifier.padding(padding),
                        onDone = { name, polygon ->  }
                    )*/
                    /*FirstScreen(
                        modifier = Modifier.padding(padding)
                    )*/
                    /*FieldsScreen(
                        modifier = Modifier.padding(padding)
                    )*/
                    /*WeatherScreen(
                        modifier = Modifier.padding(padding)
                    )*/
                    /*SoilScreen(
                        modifier = Modifier.padding(padding)
                    )*/
                    /*AIPredictionsScreen(
                        modifier = Modifier.padding(padding)
                    )*/
                    val viewModel: LandDashboardViewModel = viewModel()
                    val landState by viewModel.landState.collectAsStateWithLifecycle()

                    when (val land = landState) {
                        is UIState.Succes<Land> -> ForecastScreen(
                            modifier = Modifier.padding(padding),
                            land = land.result
                        )
                        else -> {}
                    }


                }
            }
        }
    }
}