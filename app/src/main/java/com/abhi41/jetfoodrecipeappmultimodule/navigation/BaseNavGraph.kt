package com.abhi41.jetfoodrecipeappmultimodule.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController

interface BaseNavGraph {

    fun build(
        modifier: Modifier = Modifier.Companion,
        navController: NavHostController,
        navGraphBuilder: NavGraphBuilder
    )

}