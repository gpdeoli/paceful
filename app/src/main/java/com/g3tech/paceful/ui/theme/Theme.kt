package com.g3tech.paceful.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = MellowOrange,
    onPrimary = DeepCoarchal,
    secondary = FadedTerracotta,
    onSecondary = DeepCoarchal,
    tertiary = MutedRose,
    onTertiary = DeepCoarchal,
    background = DeepCoarchal,
    onBackground = LightParchment,
    surface = DeepCoarchal,
    onSurface = LightParchment,
    surfaceVariant = WarmStone,
    onSurfaceVariant = LightParchment,
    outline = MutedGray
)

private val LightColorScheme = lightColorScheme(
    primary = MellowOrange,
    onPrimary = DeepBrown,
    secondary = FadedTerracotta,
    onSecondary = Parchment,
    tertiary = MutedRose,
    onTertiary = DeepBrown,
    background = Parchment,
    onBackground = Color.Black,
    surface = Parchment,
    onSurface = Color.Black,
    surfaceVariant = CozyTan,
    onSurfaceVariant = DeepBrown,
    outline = SoftGray
)

private val LightExtensionColors = ThemeExtension(
    scheduledColor = Orange100,
    inProgressColor = Pink100,
    pendingColor = Yellow100,
    doneColor = Green100,
)

private val DarkExtensionColors = ThemeExtension(
    scheduledColor = Orange900.copy(alpha = 0.3f),
    inProgressColor = Pink900.copy(alpha = 0.3f),
    pendingColor = Yellow900.copy(0.3f),
    doneColor = Green900.copy(alpha = 0.3f),
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    val themeExtensionColors = if (darkTheme) DarkExtensionColors else LightExtensionColors

    CompositionLocalProvider(LocalThemeExtension provides themeExtensionColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = Typography,
            motionScheme = MotionScheme.expressive(),
            content = content
        )
    }
}

object AppThemeExtension {
    val colors: ThemeExtension
        @Composable
        get() = LocalThemeExtension.current
}