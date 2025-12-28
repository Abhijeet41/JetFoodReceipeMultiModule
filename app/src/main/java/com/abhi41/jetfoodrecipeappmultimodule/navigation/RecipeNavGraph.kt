package com.abhi41.jetfoodrecipeappmultimodule.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.abhi41.receipe.presentation.dashBoard.DashBoardScreen
import com.abhi41.receipe.presentation.splash.SplashScreen
import kotlinx.serialization.Serializable

object RecipeNavGraph : BaseNavGraph{

    sealed interface Destination {
        @Serializable
        data object Root : Destination

        @Serializable
        data object Dashboard : Destination

        @Serializable
        data object SplashScreen : Destination


    }

    override fun build(
        modifier: Modifier,
        navController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    ) {
        navGraphBuilder.navigation<Destination.Root>(
            startDestination = Destination.SplashScreen
        ) {
            composable<Destination.SplashScreen> {
                SplashScreen(modifier = modifier.fillMaxSize()){
                    navController.navigate(Destination.Dashboard)
                }
            }
            composable<Destination.Dashboard> {
                DashBoardScreen(modifier = modifier.fillMaxSize())
            }

        }
    }
}