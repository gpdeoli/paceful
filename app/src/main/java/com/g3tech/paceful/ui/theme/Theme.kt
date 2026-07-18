package com.g3tech.paceful.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MotionScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

private val LightColorScheme = lightColorScheme(
    primary          = TerracottaRed,
    onPrimary        = WarmIvory,
    secondary        = TerracottaMid,
    onSecondary      = WarmIvory,
    tertiary         = AmberGold,
    onTertiary       = DeepBrown,
    background       = WarmIvory,
    onBackground     = NearBlack,
    surface          = WarmIvory,
    onSurface        = NearBlack,
    surfaceVariant   = WarmSand,
    onSurfaceVariant = DeepWarmBrown,
    outline          = WarmStoneLight,
    error            = ErrorRed,
)

private val DarkColorScheme = darkColorScheme(
    primary          = SalmonPink,
    onPrimary        = DarkBg,
    secondary        = TerracottaMid,
    onSecondary      = DarkBg,
    tertiary         = WarmGold,
    onTertiary       = DarkBg,
    background       = DarkBg,
    onBackground     = LightParchment,
    surface          = DarkSurface1,
    onSurface        = LightParchment,
    surfaceVariant   = DarkSurface3,
    onSurfaceVariant = MutedRose,
    outline          = DarkSurface4,
    error            = ErrorRed,
)

private val LightExtensionColors = ThemeExtension(
    inProgress = StatusColor(TerracottaRed.copy(alpha = 0.1f), TerracottaRed),
    scheduled  = StatusColor(WarmStoneLight, DeepWarmBrown),
    pending    = StatusColor(AmberGold.copy(alpha = 0.3f), DarkAmber),
    done       = StatusColor(WarmSand, DeepWarmBrown),
    overdue    = StatusColor(WarmStoneLight.copy(alpha = 0.5f), DeepWarmBrown),
)

private val DarkExtensionColors = ThemeExtension(
    inProgress = StatusColor(SalmonPink.copy(alpha = 0.1f), SalmonPink),
    scheduled  = StatusColor(DarkSurface4, MutedRose),
    pending    = StatusColor(WarmGold.copy(alpha = 0.2f), WarmGold),
    done       = StatusColor(DarkSurface2, MutedRose),
    overdue    = StatusColor(MutedRose.copy(alpha = 0.15f), MutedRose),
)

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme
    val extensionColors = if (darkTheme) DarkExtensionColors else LightExtensionColors

    CompositionLocalProvider(LocalThemeExtension provides extensionColors) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography  = Typography,
            shapes      = AppShapes,
            motionScheme = MotionScheme.expressive(),
            content     = content
        )
    }
}

object AppThemeExtension {
    val colors: ThemeExtension
        @Composable
        get() = LocalThemeExtension.current
}
