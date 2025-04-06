package com.techtornado.farmmonitor.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.techtornado.farmmonitor.UIState
import com.techtornado.farmmonitor.data.Land
import com.techtornado.farmmonitor.data.Polygon

@Composable
fun FieldsScreen(
    modifier: Modifier = Modifier,
    viewModel: FieldsViewModel = viewModel(),
    navigateToId: (String) -> Unit,
    navToAdd: () -> Unit
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    when (val landList = state) {
        is UIState.Error -> Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Womp womp", style = MaterialTheme.typography.headlineLarge.copy(color = MaterialTheme.colorScheme.error))
        }
        is UIState.Loading -> Loading(modifier.fillMaxSize())
        is UIState.Succes<List<Polygon>> -> FieldsScreen(modifier, landList.result, navigateToId = navigateToId, navToAdd = navToAdd)
    }
}

@Composable
fun FieldsScreen(
    modifier: Modifier = Modifier,
    landList: List<Polygon>,
    navigateToId: (String) -> Unit,
    navToAdd: () -> Unit
) {

    Column(modifier = modifier){


        Surface(
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(){
                Text("Your fields" ,
                    modifier.padding(16.dp),
                    style = MaterialTheme.typography.headlineLarge ,
                    fontStyle = FontStyle.Italic
                )
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            LazyColumn(
                contentPadding = PaddingValues(16.dp)
            ) {

                itemsIndexed(landList) { index, land ->
                    Box(modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp), contentAlignment = Alignment.Center) {
                        Surface(
                            onClick = {
                                navigateToId(land.id)
                            },
                            color = MaterialTheme.colorScheme.primaryContainer,
                            shape = RoundedCornerShape(16.dp),
                            modifier = Modifier.height(100.dp)
                        ) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(land.name)
                            }
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.BottomCenter
                            ) {
                                Text(land.area.toString())
                            }
                        }
                    }

                }
            }

            FloatingActionButton(
                onClick = {navToAdd()},
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(Icons.Default.Add,"")
            }
        }
    }
}