package com.abhi41.receipe.presentation.navigation

import androidx.annotation.DrawableRes
import com.abhi41.receipe.presentation.R

sealed class BottomNavScreen(
    val route: String,
    val title: String,
    @DrawableRes val icon: Int
) {
    object Recipes : BottomNavScreen(
        route = "recipes",
        title = "Recipes",
        icon = R.drawable.ic_recipes
    )
    object Favorites  : BottomNavScreen(
        route = "favorites",
        title = "favorites",
        icon = R.drawable.ic_favorite
    )
    object Joke: BottomNavScreen(
        route = "joke",
        title = "joke",
        icon = R.drawable.ic_joke

    )
}