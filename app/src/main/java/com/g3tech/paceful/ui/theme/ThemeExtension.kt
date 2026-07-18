package com.g3tech.paceful.ui.theme

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class StatusColor(
    val container: Color,
    val content: Color,
)

data class ThemeExtension(
    val inProgress: StatusColor,
    val scheduled: StatusColor,
    val pending: StatusColor,
    val done: StatusColor,
    val overdue: StatusColor,
)

val LocalThemeExtension = staticCompositionLocalOf {
    ThemeExtension(
        inProgress = StatusColor(Color.Unspecified, Color.Unspecified),
        scheduled  = StatusColor(Color.Unspecified, Color.Unspecified),
        pending    = StatusColor(Color.Unspecified, Color.Unspecified),
        done       = StatusColor(Color.Unspecified, Color.Unspecified),
        overdue    = StatusColor(Color.Unspecified, Color.Unspecified),
    )
}
