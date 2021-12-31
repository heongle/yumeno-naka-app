package com.yumenonaka.ymnkapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.yumenonaka.ymnkapp.models.app.RouteData

@Composable
fun Router(routes: List<RouteData>, navController: NavHostController) {
    NavHost(navController = navController, startDestination = "schedule") {
        routes.map { routeData ->
            composable(routeData.route) { routeData.composable(it) }
        }
    }
}
