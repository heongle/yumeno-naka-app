package com.yumenonaka.ymnkapp

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Router(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "schedule") {
        routes.map { route ->
            composable(route.route, content = route.content, deepLinks = route.deepLink)
        }
    }
}
