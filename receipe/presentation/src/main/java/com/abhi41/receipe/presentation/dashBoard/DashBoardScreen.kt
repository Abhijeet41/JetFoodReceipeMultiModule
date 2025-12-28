package com.abhi41.receipe.presentation.dashBoard

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.abhi41.receipe.presentation.navigation.BottomNavScreen
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abhi41.receipe.presentation.R
import com.abhi41.receipe.presentation.favorite.FavoritesScreen
import com.abhi41.receipe.presentation.joke.JokeScreen
import com.abhi41.receipe.presentation.recipes.RecipesScreen
import com.abhi41.receipe.ui.theme.titleColor

@Composable
fun DashBoardScreen(modifier: Modifier = Modifier) {
    val navController: NavHostController = rememberNavController()
    // 2. Get the current route to use in the BackHandler logic
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        BottomNavScreen.Recipes,
        BottomNavScreen.Favorites,
        BottomNavScreen.Joke
    )
    // 3. Get the OnBackPressedDispatcher to manually trigger a back press
    val backPressedDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

    // 4. Implement the BackHandler
    BackHandler(enabled = true) {
        // If we are not on the main "Recipes" screen, navigate back to it
        if (currentRoute != BottomNavScreen.Recipes.route) {
            navController.navigate(BottomNavScreen.Recipes.route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
            }
        } else {
            // Otherwise, if we are on the "Recipes" screen, perform the default back action (exit the app)
            backPressedDispatcher?.onBackPressed()
        }
    }


    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, items)
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {

                },
                modifier = Modifier.offset(x = -10.dp, y = -40.dp),
                contentColor = White
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_restaurant_24),
                    contentDescription = "restaurant icon "
                )
            }
        },
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavScreen.Recipes.route,
            modifier = modifier.padding(innerPadding)
        ) {
            composable(BottomNavScreen.Recipes.route) {
                RecipesScreen(modifier)
            }
            composable(BottomNavScreen.Favorites.route) {
                FavoritesScreen(modifier)
            }
            composable(BottomNavScreen.Joke.route) {
                JokeScreen(modifier)
            }
        }

    }
}

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<BottomNavScreen>
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar {
        items.forEach { screen ->
            NavigationBarItem(
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        // Avoid building up a large back stack
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        // Avoid multiple copies of the same destination
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = screen.icon),
                        contentDescription = screen.title
                    )
                },
                label = {
                    Text(
                        text = screen.title,
                        color = MaterialTheme.colorScheme.titleColor
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    unselectedIconColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    indicatorColor = Color(0xFF6200EE) // background for selected item
                )
            )
        }
    }


}