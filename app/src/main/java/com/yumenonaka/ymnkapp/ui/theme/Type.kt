/**
 * [About Used Font]
 * Name: Yozai Font
 * License: SIL Open Font License 1.1
 * Link: https://github.com/lxgw/yozai-font
 */
package com.yumenonaka.ymnkapp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import com.yumenonaka.ymnkapp.R

// Set of Material typography styles to start with
val Typography = Typography().defaultFontFamily(FontFamily(Font(R.font.yozai_medium)))

fun Typography.defaultFontFamily(fontFamily: FontFamily): Typography {
    return this.copy(
        displayLarge = this.displayLarge.copy(fontFamily = fontFamily),
        displayMedium = this.displayMedium.copy(fontFamily = fontFamily),
        displaySmall = this.displaySmall.copy(fontFamily = fontFamily),
        headlineLarge = this.headlineLarge.copy(fontFamily = fontFamily),
        headlineMedium = this.headlineMedium.copy(fontFamily = fontFamily),
        headlineSmall = this.headlineSmall.copy(fontFamily = fontFamily),
        titleLarge = this.titleLarge.copy(fontFamily = fontFamily),
        titleMedium = this.titleMedium.copy(fontFamily = fontFamily),
        titleSmall = this.titleSmall.copy(fontFamily = fontFamily),
        bodyLarge = this.bodyLarge.copy(fontFamily = fontFamily),
        bodyMedium = this.bodyMedium.copy(fontFamily = fontFamily),
        bodySmall = this.bodySmall.copy(fontFamily = fontFamily),
        labelLarge = this.labelLarge.copy(fontFamily = fontFamily),
        labelMedium = this.labelMedium.copy(fontFamily = fontFamily),
        labelSmall = this.labelSmall.copy(fontFamily = fontFamily),
    )
}
