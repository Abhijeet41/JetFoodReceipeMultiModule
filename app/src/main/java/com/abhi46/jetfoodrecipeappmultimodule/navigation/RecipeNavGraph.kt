package com.abhi46.jetfoodrecipeappmultimodule.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.abhi46.jetfoodrecipeappmultimodule.navigation.navType.ResultNavType
import com.abhi46.receipe.domain.models.RecipeResult
import com.abhi46.receipe.presentation.dashBoard.DashBoardScreen
import com.abhi46.receipe.presentation.recipeDetail.DetailedScreen
import com.abhi46.receipe.presentation.search.SearchScreen
import com.abhi46.receipe.presentation.splash.SplashScreen
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

object RecipeNavGraph : BaseNavGraph {

    sealed interface Destination {
        @Serializable
        data object Root : Destination

        @Serializable
        data object Dashboard : Destination

        @Serializable
        data object SplashScreen : Destination

        @Serializable
        data class DetailedScreen(
            val recipeResult: RecipeResult
        ) : Destination

        @Serializable
        data object Search : Destination

        @Serializable
        data object Chat : Destination
    }

    override fun build(
        modifier: Modifier,
        navController: NavHostController,
        navGraphBuilder: NavGraphBuilder,
        isLightMode: Boolean,
        onThemeUpdated: () -> Unit
    ) {

        navGraphBuilder.navigation<Destination.Root>(
            startDestination = Destination.SplashScreen
        ) {

            composable<Destination.SplashScreen> {
                SplashScreen(onNavigate = {
                    navController.navigate(Destination.Dashboard) {
                        popUpTo(Destination.SplashScreen) {
                            inclusive =
                                true // Set to true to remove SplashScreen itself from the back stack
                        }
                    }

                })
            }
            composable<Destination.Dashboard> {

                DashBoardScreen(
                    modifier = modifier.fillMaxSize(),
                    onSearchClicked = {
                        navController.navigate(
                            Destination.Search
                        )
                    },
                    onChatClicked = {
                        navController.navigate(
                            Destination.Chat
                        )
                    },
                    onNavigationClick = { result ->
                        // navController.navigateToDetails(result)
                        navController.navigate(
                            Destination.DetailedScreen(result)
                        )
                    },
                    isLightMode = isLightMode,
                    onThemeUpdated = onThemeUpdated
                )
            }
            composable<Destination.DetailedScreen>(
                typeMap = mapOf(
                    typeOf<RecipeResult>() to ResultNavType
                )
            ) { backStackEntry ->
                val arguments = backStackEntry.toRoute<Destination.DetailedScreen>()

                DetailedScreen(
                    recipeResult = arguments.recipeResult
                ) {
                    navController.popBackStack()
                }
            }
            composable<Destination.Search> {

                SearchScreen(
                    onClosedClicked = {
                        navController.popBackStack()
                    },
                    onNavigationClick = {result ->
                        navController.navigate(
                            Destination.DetailedScreen(result)
                        )
                    }
                )
            }
            
            composable<Destination.Chat> {
                com.abhi46.receipe.presentation.chat.ChatScreen(
                    onBackClicked = {
                        navController.popBackStack()
                    }
                )
            }

        }
    }
}
