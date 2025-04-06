package com.techtornado.farmmonitor

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import com.techtornado.farmmonitor.data.Land
import com.techtornado.farmmonitor.screens.AIPredictionsScreen
import com.techtornado.farmmonitor.screens.CreateLandScreen
import com.techtornado.farmmonitor.screens.FieldsScreen
import com.techtornado.farmmonitor.screens.LandDashboard
import kotlinx.serialization.Serializable

@Serializable
object ListScreen
@Serializable
data class Dashboard(val fieldId: String)
@Serializable
data class AIScreen(val landid: String)
@Serializable
data object Add

@Composable
fun Navigation(modifier: Modifier = Modifier) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ListScreen
    ) {
        composable<ListScreen> {
            FieldsScreen(navigateToId = {
                navController.navigate(route = Dashboard(it))
            },
                navToAdd = { navController.navigate(route = Add) }
                )
        }
        composable<Dashboard> { backStackEntry ->
            val dashboard: Dashboard = backStackEntry.toRoute()
            LandDashboard(landId = dashboard.fieldId, navToAi = { navController.navigate(route = AIScreen(it)) })
        }
        composable<AIScreen> { navBackStackEntry ->
            val ai: AIScreen = navBackStackEntry.toRoute()
            AIPredictionsScreen(landid = ai.landid)
        }
        composable<Add> {
            CreateLandScreen()
        }
    }
}