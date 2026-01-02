@file:OptIn(ExperimentalMaterial3Api::class)

package com.abhi41.receipe.presentation.dashBoard

import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
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
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abhi41.receipe.domain.models.RecipeResult
import com.abhi41.receipe.domain.utils.Diet
import com.abhi41.receipe.domain.utils.DietType
import com.abhi41.receipe.domain.utils.Meal
import com.abhi41.receipe.domain.utils.MealType
import com.abhi41.receipe.presentation.R
import com.abhi41.receipe.presentation.common.chip.DietTypeChipGroup
import com.abhi41.receipe.presentation.common.chip.MealTypeChipGroup
import com.abhi41.receipe.presentation.favorite.FavoritesScreen
import com.abhi41.receipe.presentation.joke.JokeScreen
import com.abhi41.receipe.presentation.recipes.RecipesScreen
import com.abhi41.receipe.presentation.recipes.RecipesViewModel
import com.abhi41.receipe.ui.theme.LARGE_PADDING
import com.abhi41.receipe.ui.theme.SMALL_PADDING
import com.abhi41.receipe.ui.theme.TXT_MEDIUM_SIZE
import com.abhi41.receipe.ui.theme.buttonColor
import com.abhi41.receipe.ui.theme.titleColor
import com.abhi41.receipe.ui.theme.topAppBarBackgroundColor
import com.abhi41.receipe.ui.theme.topAppBarContentColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoardScreen(modifier: Modifier = Modifier, onNavigationClick: (RecipeResult) -> Unit) {
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
    val backHandlingEnabled = currentRoute != BottomNavScreen.Recipes.route
    // 4. Implement the BackHandler
    BackHandler(enabled = backHandlingEnabled) {
            navController.navigate(BottomNavScreen.Recipes.route) {
                popUpTo(navController.graph.startDestinationId)
                launchSingleTop = true
        }
    }

    // --- Bottom Sheet State ---
    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }

    Scaffold(
        bottomBar = {
            BottomNavigationBar(navController, items)
        },
        floatingActionButton = {
            // Only show the FAB if the current route is the Recipes screen
            if (currentRoute == BottomNavScreen.Recipes.route) {
                if (!sheetState.isVisible) {    //FAB will be visible only when bottom sheet is hidden
                    FloatingActionButton(
                        onClick = {
                            scope.launch {
                                if (sheetState.isVisible) {
                                    sheetState.hide()
                                    showBottomSheet = false
                                } else {
                                    showBottomSheet = true
                                    sheetState.show()
                                }
                            }
                        },
                        modifier = Modifier.offset(x = -10.dp, y = -40.dp),
                        contentColor = White
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_restaurant_24),
                            contentDescription = "restaurant icon "
                        )
                    }
                }
            }

        },
        topBar = {
            RecipesTopBar() {}
        },
    ) { innerPadding ->

        if (showBottomSheet) {
            BottomSheetDesign(
                sheetState,
                onSelect = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            showBottomSheet = false
                        }
                    }
                }, onDismiss = {
                    showBottomSheet = false
                }
            )
        }

        NavHost(
            navController = navController,
            startDestination = BottomNavScreen.Recipes.route
        ) {
            composable(BottomNavScreen.Recipes.route) {
                RecipesScreen(
                    Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    onNavigationClick = { result ->
                        onNavigationClick(result)
                    }
                )
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
@OptIn(ExperimentalMaterial3Api::class)
private fun BottomSheetDesign(
    sheetState: SheetState,
    onSelect: () -> Unit,
    onDismiss: () -> Unit
) {
    val viewmodel :RecipesViewModel = hiltViewModel()
    var selectedMeal = viewmodel.selectedMealType
    var selectedDiet = viewmodel.selectedDietType

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        // Sheet content
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(start = SMALL_PADDING),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.padding(
                    top = LARGE_PADDING,
                    start = LARGE_PADDING
                ),
                text = "Meal Type",
                fontWeight = FontWeight.Bold,
                fontSize = TXT_MEDIUM_SIZE,
            )
            MealTypeChipGroup(
                meals = MealType.getMeals(),
                selectedMeal = selectedMeal.value,
                onSelectedChange = {text ->
                 selectedMeal.value = Meal(text)
                }
            )
            Text(
                modifier = Modifier.padding(
                    top = LARGE_PADDING,
                    start = LARGE_PADDING
                ),
                text = "Diet Type",
                fontWeight = FontWeight.Bold,
                fontSize = TXT_MEDIUM_SIZE,
            )
            DietTypeChipGroup(
                diets = DietType.getDiets(),
                selectedDiet = selectedDiet.value,
                onSelectedChange = { text ->
                    selectedDiet.value = Diet(text)
                }
            )
            Spacer(Modifier.height(20.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.4f),
                    onClick = {
                        viewmodel.getRecipes(selectedMeal.value, selectedDiet.value)
                        onSelect()
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.buttonColor
                    )
                ) {
                    Text( text = "Apply",
                        color = White,
                        textAlign = TextAlign.Center)
                }
            }
        }
    }

}

@Composable
fun RecipesTopBar(
    onSearchClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Search...",
                color = MaterialTheme.colorScheme.topAppBarContentColor
            )
        }, colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroundColor
        ),
        actions = {
            IconButton(onClick = {
                onSearchClicked()
            }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icons",
                    tint = Color.White
                )
            }
        }
    )
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

@Preview
@Composable
private fun DashBoardScreenPrev() {
    DashBoardScreen(modifier = Modifier){}
}