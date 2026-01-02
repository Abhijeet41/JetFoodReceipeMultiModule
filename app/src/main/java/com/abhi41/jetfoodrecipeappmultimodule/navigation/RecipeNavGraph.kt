package com.abhi41.jetfoodrecipeappmultimodule.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import androidx.navigation.toRoute
import com.abhi41.jetfoodrecipeappmultimodule.navigation.navType.ResultNavType
import com.abhi41.receipe.domain.models.RecipeResult
import com.abhi41.receipe.presentation.dashBoard.DashBoardScreen
import com.abhi41.receipe.presentation.recipeDetail.DetailedScreen
import com.abhi41.receipe.presentation.splash.SplashScreen
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import java.net.URLEncoder
import java.nio.charset.StandardCharsets
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
                SplashScreen(modifier = modifier.fillMaxSize()) {
                    navController.navigate(Destination.Dashboard) {
                        popUpTo(Destination.SplashScreen) {
                            inclusive =
                                true // Set to true to remove SplashScreen itself from the back stack
                        }
                    }

                }
            }
            composable<Destination.Dashboard> {
                DashBoardScreen(modifier = modifier.fillMaxSize()) { result ->
                   // navController.navigateToDetails(result)
                    navController.navigate(
                        Destination.DetailedScreen(result)
                    )
                }
            }
            composable<Destination.DetailedScreen>(
                typeMap = mapOf(
                    typeOf<RecipeResult>() to ResultNavType
                )
            ) {backStackEntry ->
                val arguments = backStackEntry.toRoute<Destination.DetailedScreen>()

                DetailedScreen(
                    modifier = modifier.fillMaxSize(),
                    recipeResult = arguments.recipeResult
                )
            }

        }
    }
}

fun NavController.navigateToDetails(result: RecipeResult) {
    val json = Json.encodeToString(result)
    val encoded = URLEncoder.encode(json, StandardCharsets.UTF_8.toString())

    navigate("DetailedScreen/$encoded")
}