package com.techtornado.farmmonitor

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.techtornado.farmmonitor.screens.CreateLandScreen
import com.techtornado.farmmonitor.screens.FirstScreen
import com.techtornado.farmmonitor.screens.LandDashboard
import com.techtornado.farmmonitor.ui.theme.FarmMonitorTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FarmMonitorTheme {
                Surface(
                    modifier = Modifier.fillMaxSize()
                ) {
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
                    LandDashboard()
                }
            }
        }
    }
}