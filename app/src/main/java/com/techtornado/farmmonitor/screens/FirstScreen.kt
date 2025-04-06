package com.techtornado.farmmonitor.screens

import android.util.Log
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCompositionContext
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.android.gms.maps.model.LatLng
import com.techtornado.farmmonitor.FirstViewModel
import com.techtornado.farmmonitor.MainViewModel
import com.techtornado.farmmonitor.R
import com.techtornado.farmmonitor.data.Forecast
import com.techtornado.farmmonitor.data.Weather
import com.techtornado.farmmonitor.data.Polygon
import com.techtornado.farmmonitor.data.Soil
import com.techtornado.farmmonitor.data.Stat
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@Composable
fun FirstScreen(
    modifier: Modifier = Modifier,
    viewModel: FirstViewModel = viewModel()
){
    Column(modifier.padding(16.dp)) {

        Text("IONELA" ,

            style = MaterialTheme.typography.headlineLarge ,
            fontStyle = FontStyle.Italic
        )

        Spacer(Modifier.height(16.dp))

        Box(
            modifier=Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        )
        {
            Surface(
                onClick = {},
                color = MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(200.dp,200.dp),

                ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ){
                    Text(
                        "Add your first field",
                    )
                }
            }
        }

    }
}