package com.abhi41.receipe.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Green
import androidx.compose.ui.graphics.Color.Companion.LightGray

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Purple200 = Color(0xFFBB86FC)

val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)

//🔹Text colors
val ColorScheme.titleColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) DarkGray else LightGray

val ColorScheme.descriptionColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme())
        Color.Black.copy(alpha = 0.5f)
    else
        LightGray.copy(alpha = 0.6f)

//🔹Buttons & indicators
val ColorScheme.buttonColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Purple700 else Color.Black

val ColorScheme.readyInMinute: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.Black else Color.Yellow

//🔹TopAppBar
val ColorScheme.topAppBarContentColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.White else LightGray

val ColorScheme.topAppBarBackgroundColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Purple500 else Color.Black

//🔹 Screen & card backgrounds
val ColorScheme.screenBackgroundColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.White else DarkGray

val ColorScheme.cardStrokeBorder: Color
    @Composable
    get() = if (!isSystemInDarkTheme())
        Color.Black.copy(alpha = 0.3f)
    else
        Color.White

//🔹 Tabs & categories
val ColorScheme.tabBackgroundColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Purple500 else Color.Black

val ColorScheme.categoriesBackgroundColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.White else Color.Black

val ColorScheme.categoriesIconColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.Black else Color.White

val ColorScheme.categoriesSelectedIconColor: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Green else Green

//🔹 Misc
val ColorScheme.txtFoodJoke: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.Black else Color.White

val ColorScheme.motionLayoutBg: Color
    @Composable
    get() = if (!isSystemInDarkTheme()) Color.White else Color.Black
