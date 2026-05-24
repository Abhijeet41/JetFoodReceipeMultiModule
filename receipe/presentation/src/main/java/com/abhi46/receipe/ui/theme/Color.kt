package com.abhi46.receipe.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.luminance

// --- Brand Colors (Light Mode) ---
val PrimaryOrange = Color(0xFF994700)
val OnPrimaryOrange = Color(0xFFFFFFFF)
val PrimaryOrangeContainer = Color(0xFFFF7A00)
val OnPrimaryOrangeContainer = Color(0xFF5C2800)

val SecondaryTomato = Color(0xFFB12C16)
val OnSecondaryTomato = Color(0xFFFFFFFF)
val SecondaryTomatoContainer = Color(0xFFFE6247)
val OnSecondaryTomatoContainer = Color(0xFF620900)

val TertiaryWarm = Color(0xFF635E53)
val OnTertiaryWarm = Color(0xFFFFFFFF)
val TertiaryWarmContainer = Color(0xFFA6A093)
val OnTertiaryWarmContainer = Color(0xFF3B372D)

val CreamBackground = Color(0xFFFCF9F8)
val DarkCharcoalText = Color(0xFF1C1B1B)
val LightSurfaceVariant = Color(0xFFE5E2E1)
val DarkOutline = Color(0xFF8C7263)
val OutlineVariantOrange = Color(0xFFE0C0AF)

// --- Brand Colors (Dark Mode) ---
val PrimaryOrangeDark = Color(0xFFFFB689)
val OnPrimaryOrangeDark = Color(0xFF512300)
val PrimaryOrangeContainerDark = Color(0xFF723A0F)
val OnPrimaryOrangeContainerDark = Color(0xFFF6A672)

val SecondaryTomatoDark = Color(0xFFFFB689)
val OnSecondaryTomatoDark = Color(0xFF512300)
val SecondaryTomatoContainerDark = Color(0xFF723A0F)
val OnSecondaryTomatoContainerDark = Color(0xFFF6A672)

val TertiaryDark = Color(0xFF91CDFF)
val OnTertiaryDark = Color(0xFF003350)
val TertiaryContainerDark = Color(0xFF00ABFD)
val OnTertiaryContainerDark = Color(0xFF003C5D)

val CharcoalBackgroundDark = Color(0xFF1C110A)
val CreamTextDark = Color(0xFFF5DED2)
val DarkSurfaceVariant = Color(0xFF40322A)
val OutlineDark = Color(0xFFA68B7C)
val OutlineVariantDark = Color(0xFF584235)

// --- M3 Legacy Imports ---
val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650A4)
val PurpleGrey40 = Color(0xFF625B71)
val Pink40 = Color(0xFF7D5260)

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val DarkYellow = Color(0xFFFAC213)

// --- ColorScheme Extensions to Preserve Business Logic & UI References ---
val ColorScheme.isLight: Boolean
    @Composable
    get() = background.luminance() > 0.5f

val ColorScheme.titleColor: Color
    @Composable
    get() = if (isLight) DarkCharcoalText else CreamTextDark

val ColorScheme.descriptionColor: Color
    @Composable
    get() = if (isLight) DarkCharcoalText.copy(alpha = 0.6f) else CreamTextDark.copy(alpha = 0.6f)

val ColorScheme.buttonColor: Color
    @Composable
    get() = if (isLight) PrimaryOrange else PrimaryOrangeDark

val ColorScheme.readyInMinute: Color
    @Composable
    get() = if (isLight) DarkCharcoalText.copy(alpha = 0.7f) else CreamTextDark.copy(alpha = 0.7f)

val ColorScheme.topAppBarContentColor: Color
    @Composable
    get() = if (isLight) OnPrimaryOrange else CreamTextDark

val ColorScheme.topAppBarBackgroundColor: Color
    @Composable
    get() = if (isLight) PrimaryOrange else CharcoalBackgroundDark

val ColorScheme.screenBackgroundColor: Color
    @Composable
    get() = if (isLight) CreamBackground else CharcoalBackgroundDark

val ColorScheme.cardStrokeBorder: Color
    @Composable
    get() = if (isLight) OutlineVariantOrange.copy(alpha = 0.4f) else OutlineVariantDark.copy(alpha = 0.4f)

val ColorScheme.tabBackgroundColor: Color
    @Composable
    get() = if (isLight) PrimaryOrange else CharcoalBackgroundDark

val ColorScheme.categoriesBackgroundColor: Color
    @Composable
    get() = if (isLight) CreamBackground else CharcoalBackgroundDark

val ColorScheme.categoriesIconColor: Color
    @Composable
    get() = if (isLight) DarkCharcoalText.copy(alpha = 0.5f) else CreamTextDark.copy(alpha = 0.5f)

val ColorScheme.categoriesSelectedIconColor: Color
    @Composable
    get() = Color(0xFF2E7D32) // Forest green for vegan checkmarks

val ColorScheme.txtFoodJoke: Color
    @Composable
    get() = if (isLight) DarkCharcoalText else CreamTextDark

val ColorScheme.motionLayoutBg: Color
    @Composable
    get() = if (isLight) CreamBackground else CharcoalBackgroundDark

val ColorScheme.strokeBorderColor: Color
    @Composable
    get() = if (isLight) PrimaryOrange else PrimaryOrangeDark