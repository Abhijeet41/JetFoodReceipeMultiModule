package com.abhi41.jetfoodrecipeappmultimodule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.abhi41.jetfoodrecipeappmultimodule.navigation.RecipeNavGraph
import com.abhi41.jetfoodrecipeappmultimodule.ui.theme.JetFoodRecipeAppMultiModuleTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.core.view.WindowCompat
import coil.ImageLoader
import com.abhi41.receipe.presentation.new_recipe_ui.CulinaShareTheme
import com.abhi41.receipe.presentation.new_recipe_ui.RecipesScreen
import com.abhi41.receipe.presentation.utils.LocalImageLoader
import com.google.firebase.analytics.FirebaseAnalytics
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

   @Inject
    lateinit var imageLoader: ImageLoader
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        //WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            // Lift dark mode state to MainActivity
            val systemTheme = isSystemInDarkTheme()
            var isLightMode = rememberSaveable { mutableStateOf(systemTheme) }
            CompositionLocalProvider(LocalImageLoader provides imageLoader) {
                JetFoodRecipeAppMultiModuleTheme(
                    darkTheme = isLightMode.value
                ) {
                    val navhostController = rememberNavController()

                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        NavHost(
                            navController = navhostController,
                            startDestination = RecipeNavGraph.Destination.Root
                        ) {
                            listOf(
                                RecipeNavGraph
                            ).forEach {
                                it.build(
                                    modifier = Modifier
                                        .padding(innerPadding)
                                        .fillMaxSize(),
                                    navController = navhostController,
                                    navGraphBuilder = this,
                                    isLightMode = isLightMode.value,
                                    onThemeUpdated = { isLightMode.value = !isLightMode.value }
                                )

                            }
                        }

                    }
                }
            }
        }

        /*setContent {
            CulinaShareTheme {
                RecipesScreen()
            }
        }*/
    }
}
