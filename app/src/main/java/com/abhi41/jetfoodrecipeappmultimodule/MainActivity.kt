package com.abhi41.jetfoodrecipeappmultimodule

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import androidx.compose.ui.res.stringResource


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            JetFoodRecipeAppMultiModuleTheme {
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
                                navGraphBuilder = this
                            )

                        }
                    }

                }
            }
        }
    }
}

