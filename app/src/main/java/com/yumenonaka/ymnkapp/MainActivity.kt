package com.yumenonaka.ymnkapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.consumedWindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yumenonaka.ymnkapp.components.Drawer
import com.yumenonaka.ymnkapp.ui.theme.YumenoNakaAppTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val coroutineScope = rememberCoroutineScope()
            val navController = rememberNavController()
            YumenoNakaAppTheme {
                ModalNavigationDrawer(
                    drawerContent = { Drawer(navController, drawerState, coroutineScope) },
                    drawerState = drawerState
                ) {
                    Scaffold(
                        topBar = { YumenoTopBar(drawerState, coroutineScope, navController) },
                        content = { innerPadding ->
                            Box(
                                modifier = Modifier
                                    .consumedWindowInsets(innerPadding)
                                    .padding(innerPadding),
                            ) {
                                Router(navController)
                            }
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun YumenoTopBar(
    drawerState: DrawerState,
    coroutineScope: CoroutineScope,
    navController: NavHostController
) {
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val currentRoute = routes.find { it.route == currentDestination?.route }
    TopAppBar(
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xff8445de),
            titleContentColor = Color.White
        ),
        title = { Text(text = if(currentRoute?.resourceId != null) context.getString(currentRoute.resourceId) else "") },
        navigationIcon = {
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    contentColor = Color.White
                ),
                onClick = {
                    coroutineScope.launch { drawerState.open() }
                }
            ) {
                Icon(Icons.Filled.Menu, contentDescription = null)
            }
        }
    )
}
