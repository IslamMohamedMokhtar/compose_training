package com.example.compose.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.runtime.staticCompositionLocalOf

// Your extended colors data class
@Immutable
data class ExtendedColors(
    val background2: Color,
    val primary: Color,
    val secondary: Color,
    val tertiary: Color,
)

val LocalExtendedColors = staticCompositionLocalOf<ExtendedColors> {
    error("No ExtendedColors provided")
}

// Material3 color schemes for light and dark themes
private val DarkColorSchemeM3 = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Color(0xFF1E1D1D)
)

private val LightColorSchemeM3 = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color(0xFFFFF8F8)
)

private val DarkExtendedColors = ExtendedColors(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background2 = Color(0xFF1E1D1D)
)

private val LightExtendedColors = ExtendedColors(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background2 = Color(0xFFFFF8F8)
)

@Composable
fun ComposeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
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