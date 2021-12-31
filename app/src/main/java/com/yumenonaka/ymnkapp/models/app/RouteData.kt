package com.yumenonaka.ymnkapp.models.app

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry

data class RouteData (
    val name: String,
    val route: String,
    val needDivide: Boolean = false,
    val composable: @Composable (NavBackStackEntry) -> Unit,
)
