package com.yumenonaka.ymnkapp.ui.theme

//import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme

import androidx.compose.runtime.Composable

//private val DarkColorPalette = darkColors(
//    primary = primaryDarkColor,
//    primaryVariant = primaryColor,
//    secondary = secondaryColor
//)
//
//private val LightColorPalette = lightColors(
//    primary = primaryDarkColor,
//    primaryVariant = primaryLightColor,
//    secondary = secondaryColor
//
//    /* Other default colors to override
//    background = Color.White,
//    surface = Color.White,
//    onPrimary = Color.White,
//    onSecondary = Color.Black,
//    onBackground = Color.Black,
//    onSurface = Color.Black,
//    */
//)

@Composable
fun YumenoNakaAppTheme(
//    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
//    val colors = if (darkTheme) {
//        DarkColorPalette
//    } else {
//        LightColorPalette
//    }

    MaterialTheme(
        colorScheme = MaterialTheme.colorScheme,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
