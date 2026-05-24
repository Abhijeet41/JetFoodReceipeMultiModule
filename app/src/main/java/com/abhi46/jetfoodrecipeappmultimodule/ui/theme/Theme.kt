package com.abhi46.jetfoodrecipeappmultimodule.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = PrimaryOrangeDark,
    onPrimary = OnPrimaryOrangeDark,
    primaryContainer = PrimaryOrangeContainerDark,
    onPrimaryContainer = OnPrimaryOrangeContainerDark,
    secondary = SecondaryTomatoDark,
    onSecondary = OnSecondaryTomatoDark,
    secondaryContainer = SecondaryTomatoContainerDark,
    onSecondaryContainer = OnSecondaryTomatoContainerDark,
    tertiary = TertiaryDark,
    onTertiary = OnTertiaryDark,
    tertiaryContainer = TertiaryContainerDark,
    onTertiaryContainer = OnTertiaryContainerDark,
    background = CharcoalBackgroundDark,
    onBackground = CreamTextDark,
    surface = CharcoalBackgroundDark,
    onSurface = CreamTextDark,
    surfaceVariant = DarkSurfaceVariant,
    onSurfaceVariant = CreamTextDark.copy(alpha = 0.8f),
    outline = OutlineDark,
    outlineVariant = OutlineVariantDark
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryOrange,
    onPrimary = OnPrimaryOrange,
    primaryContainer = PrimaryOrangeContainer,
    onPrimaryContainer = OnPrimaryOrangeContainer,
    secondary = SecondaryTomato,
    onSecondary = OnSecondaryTomato,
    secondaryContainer = SecondaryTomatoContainer,
    onSecondaryContainer = OnSecondaryTomatoContainer,
    tertiary = TertiaryWarm,
    onTertiary = OnTertiaryWarm,
    tertiaryContainer = TertiaryWarmContainer,
    onTertiaryContainer = OnTertiaryWarmContainer,
    background = CreamBackground,
    onBackground = DarkCharcoalText,
    surface = CreamBackground,
    onSurface = DarkCharcoalText,
    surfaceVariant = LightSurfaceVariant,
    onSurfaceVariant = DarkCharcoalText.copy(alpha = 0.8f),
    outline = DarkOutline,
    outlineVariant = OutlineVariantOrange
)

@Composable
fun JetFoodRecipeAppMultiModuleTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
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

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}