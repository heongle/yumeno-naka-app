package com.yumenonaka.ymnkapp.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yumenonaka.ymnkapp.routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private val drawerImageList = listOf("img1", "img2", "img3", "img4", "img5", "img6", "img7", "img8", "img9", "img10", "img11", "img12", "img13")

@Composable
fun Drawer(navController: NavHostController, drawerState: DrawerState, coroutineScope: CoroutineScope) {
    val context = LocalContext.current
    val headerImages = drawerImageList.map { BitmapFactory.decodeStream(context.assets.openFd("drawer/${it}.jpg").createInputStream()).asImageBitmap() }
    ModalDrawerSheet {
        DrawerHeaderImage(drawerState, headerImages)
        DrawerContent(navController, drawerState, coroutineScope)
    }
}

@Composable
fun DrawerContent(navController: NavHostController, drawerState: DrawerState, coroutineScope: CoroutineScope) {
    val context = LocalContext.current
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    Column(
        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        routes.map { route ->
            NavigationDrawerItem(
                label = { Text(context.getString(route.resourceId)) },
                selected = navBackStackEntry?.destination?.route == route.route,
                onClick = {
                    navController.navigate(route.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                    }
                    coroutineScope.launch {
                        drawerState.close()
                    }
                },
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .height(38.dp)
            )
            if(route.needDivide) {
                Divider(Modifier.padding(vertical = 4.dp))
            }
        }
    }
}

@Composable
private fun DrawerHeaderImage(drawerState: DrawerState, headerImages: List<ImageBitmap>) {
    val prevState = remember { mutableStateOf(DrawerValue.Closed) }
    val showingImg = remember { mutableStateOf(headerImages.random()) }
    if(drawerState.isClosed && drawerState.currentValue != prevState.value) {
        showingImg.value = headerImages.random()
    }
    prevState.value = drawerState.currentValue
    Image(bitmap = showingImg.value, contentDescription = "")
}
