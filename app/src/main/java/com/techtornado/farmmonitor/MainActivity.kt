package com.techtornado.farmmonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.techtornado.farmmonitor.screens.FieldsScreen
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
                    SoilScreen(
                        modifier = Modifier.padding(padding)
                    )
                }
            }
        }
    }
}