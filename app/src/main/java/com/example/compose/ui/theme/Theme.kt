package com.example.compose.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Your extended colors data class
@Immutable
data class ExtendedColors(
    val background: Color,
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
    val onPrimary: Color,
    val colorF7F8FA: Color,
    val textAccent: Color,
    val textSecondary: Color,
    val yellow: Color,
    val titleColor: Color
)

val LocalExtendedColors = staticCompositionLocalOf<ExtendedColors> {
    error("No ExtendedColors provided")
}

private val DarkExtendedColors = ExtendedColors(
    primary = Primary,
    secondary = Secondary,
    tertiary = Pink80,
    background = Color(0xFF1E1D1D),
    onPrimary = Color777575,
    colorF7F8FA = ColorF7F8FA,
    textAccent = Color.Black,
    textSecondary = ColorBBBBBB,
    yellow = ColorFFE000,
    titleColor = Color51B6EF
)

private val LightExtendedColors = ExtendedColors(
    primary = Primary,
    secondary = Secondary,
    tertiary = Pink40,
    background = Color(0xFFFFF8F8),
    onPrimary = Color333333,
    colorF7F8FA = ColorF7F8FA,
    textAccent = Color.White,
    textSecondary = Color555555,
    yellow = ColorFFE109,
    titleColor = Color169AE5
)

private val DarkColorSchemeM3 = darkColorScheme(
    primary = DarkExtendedColors.primary,
    secondary = DarkExtendedColors.secondary,
    tertiary = Pink80,
    background = Color(0xFF1E1D1D),
    onPrimary = DarkExtendedColors.onPrimary
)

private val LightColorSchemeM3 = lightColorScheme(
    primary = LightExtendedColors.primary,
    secondary = LightExtendedColors.secondary,
    tertiary = Pink40,
    background = Color(0xFFFFF8F8),
    onPrimary = LightExtendedColors.onPrimary
)

@Composable
fun ComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val context = LocalContext.current

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorSchemeM3
        else -> LightColorSchemeM3
    }

    val extendedColors = if (darkTheme) DarkExtendedColors else LightExtendedColors

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
    ) {
        CompositionLocalProvider(LocalExtendedColors provides extendedColors) {
            content()
        }
    }
}