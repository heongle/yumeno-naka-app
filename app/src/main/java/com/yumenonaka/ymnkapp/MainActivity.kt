package com.yumenonaka.ymnkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yumenonaka.ymnkapp.components.Drawer
import com.yumenonaka.ymnkapp.models.app.RouteData
import com.yumenonaka.ymnkapp.ui.theme.YumenoNakaAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val routes = getRoutes(applicationContext)
        setContent {
            val scaffoldState = rememberScaffoldState()
            val coroutineScope = rememberCoroutineScope()
            val navController = rememberNavController()
            YumenoNakaAppTheme {
                Scaffold(
                    scaffoldState = scaffoldState,
                    drawerContent = { Drawer(routes, navController, scaffoldState, coroutineScope) },
                    drawerShape = MaterialTheme.shapes.large,
                    drawerGesturesEnabled = true,
                    topBar = { YumenoTopBar(scaffoldState, coroutineScope, navController, routes) },
                    content = { Router(routes, navController) }
                )
            }
        }
    }
}

@Composable
fun YumenoTopBar(
    scaffoldState: ScaffoldState,
    coroutineScope: CoroutineScope,
    navController: NavHostController,
    routes: List<RouteData>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    TopAppBar(
        backgroundColor = Color(0xff8445de),
        contentColor = Color.White,
        title = { Text(text = routes.find { it.route == currentDestination?.route }?.name ?: "") },
        navigationIcon = {
            IconButton(
                onClick = {
                    coroutineScope.launch { scaffoldState.drawerState.open() }
                }
            ) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        }
    )
}
