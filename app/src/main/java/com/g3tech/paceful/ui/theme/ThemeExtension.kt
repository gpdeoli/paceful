package com.g3tech.paceful.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class ThemeExtension(
    val inProgressColor : Color,
    val doneColor : Color,
    val scheduledColor : Color,
    val pendingColor : Color,
)

val LocalThemeExtension = staticCompositionLocalOf {
    ThemeExtension(
        inProgressColor = Color.Unspecified,
        doneColor = Color.Unspecified,
        scheduledColor = Color.Unspecified,
        pendingColor = Color.Unspecified,
    )
}

