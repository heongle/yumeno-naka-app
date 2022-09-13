package com.yumenonaka.ymnkapp.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yumenonaka.ymnkapp.routes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private val drawerImageList = listOf("img1", "img2", "img3", "img4", "img5", "img6", "img7", "img8", "img9", "img10", "img11", "img12", "img13")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Drawer(navController: NavHostController, drawerState: DrawerState, coroutineScope: CoroutineScope) {
    val context = LocalContext.current
    val headerImages = drawerImageList.map { BitmapFactory.decodeStream(context.assets.openFd("drawer/${it}.jpg").createInputStream()).asImageBitmap() }
    ModalDrawerSheet {
        DrawerHeaderImage(drawerState, headerImages)
        DrawerContent(navController, drawerState, coroutineScope)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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
                modifier = Modifier.padding(vertical = 4.dp).height(40.dp)
            )
            if(route.needDivide) {
                Divider()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
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

@Composable
private fun ButtonMenu(text: String, onClick: () -> Unit, selected: Boolean) {
    val buttonColors = if(selected) {
        ButtonDefaults.textButtonColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer,
            contentColor = MaterialTheme.colorScheme.primary
        )
    } else {
        ButtonDefaults.textButtonColors(
            containerColor = Color.Transparent,
            contentColor = Color.Black
        )
    }

    TextButton(
        modifier = Modifier.fillMaxWidth(),
        colors = buttonColors,
        onClick = onClick
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            fontSize = 18.sp,
            textAlign = TextAlign.Left,
            text = text
        )
    }
}
