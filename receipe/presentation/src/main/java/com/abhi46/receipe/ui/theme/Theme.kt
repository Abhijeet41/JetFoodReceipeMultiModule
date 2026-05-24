package com.abhi46.receipe.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

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
    // Set dynamicColor to false by default to showcase our premium custom design
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context)
            else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}