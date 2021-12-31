package com.yumenonaka.ymnkapp.components

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.yumenonaka.ymnkapp.models.app.RouteData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private val drawerImageList = listOf("img1", "img2", "img3", "img4", "img5", "img6", "img7", "img8", "img9", "img10", "img11", "img12", "img13")

@Composable
fun Drawer(routes: List<RouteData>, navController: NavHostController, scaffoldState: ScaffoldState, coroutineScope: CoroutineScope) {
    val context = LocalContext.current
    val headerImages = drawerImageList.map { BitmapFactory.decodeStream(context.assets.openFd("drawer/${it}.jpg").createInputStream()).asImageBitmap() }
    Column {
        DrawerHeaderImage(scaffoldState, headerImages)
        DrawerContent(routes, navController, scaffoldState, coroutineScope)
    }
}

@Composable
fun DrawerContent(routes: List<RouteData>, navController: NavHostController, scaffoldState: ScaffoldState, coroutineScope: CoroutineScope) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 8.dp)
    ) {
        routes.map { routeData ->
            ButtonMenu(
                text = routeData.name,
                onClick = {
                    navController.navigate(routeData.route)
                    coroutineScope.launch {
                        scaffoldState.drawerState.close()
                    }
                },
                selected = navBackStackEntry?.destination?.route == routeData.route
            )
            if(routeData.needDivide) {
                Divider()
            }
        }
    }
}

@Composable
private fun DrawerHeaderImage(scaffoldState: ScaffoldState, headerImages: List<ImageBitmap>) {
    val prevState = remember { mutableStateOf(DrawerValue.Closed) }
    val showingImg = remember { mutableStateOf(headerImages.random()) }
    if(scaffoldState.drawerState.isClosed && scaffoldState.drawerState.currentValue != prevState.value) {
        showingImg.value = headerImages.random()
    }
    prevState.value = scaffoldState.drawerState.currentValue
    Image(bitmap = showingImg.value, contentDescription = "")
}

@Composable
private fun ButtonMenu(text: String, onClick: () -> Unit, selected: Boolean) {
    val buttonColors = if(selected) {
        ButtonDefaults.textButtonColors(
            backgroundColor = MaterialTheme.colors.secondary,
            contentColor = MaterialTheme.colors.primary
        )
    } else {
        ButtonDefaults.textButtonColors(
            backgroundColor = Color.Transparent,
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
