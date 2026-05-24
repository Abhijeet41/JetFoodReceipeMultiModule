@file:OptIn(ExperimentalMaterial3Api::class)

package com.abhi46.receipe.presentation.dashBoard

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.abhi46.receipe.domain.models.RecipeResult
import com.abhi46.receipe.domain.utils.Diet
import com.abhi46.receipe.domain.utils.DietType
import com.abhi46.receipe.domain.utils.Meal
import com.abhi46.receipe.domain.utils.MealType
import com.abhi46.receipe.presentation.common.chip.DietTypeChipGroup
import com.abhi46.receipe.presentation.common.chip.MealTypeChipGroup
import com.abhi46.receipe.presentation.favorite.FavoritesScreen
import com.abhi46.receipe.presentation.joke.JokeScreen
import com.abhi46.receipe.presentation.navigation.BottomNavScreen
import com.abhi46.receipe.presentation.recipes.RecipesScreen
import com.abhi46.receipe.presentation.recipes.RecipesViewModel
import com.abhi46.receipe.ui.theme.*
import kotlinx.coroutines.launch

@Composable
fun DashBoardScreen(
    modifier: Modifier = Modifier,
    onNavigationClick: (RecipeResult) -> Unit,
    onSearchClicked: () -> Unit,
    onChatClicked: () -> Unit,
    onThemeUpdated: () -> Unit,
    isLightMode: Boolean
) {
    val navController: NavHostController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val items = listOf(
        BottomNavScreen.Recipes,
        BottomNavScreen.Favorites,
        BottomNavScreen.Joke
    )

    val backHandlingEnabled = currentRoute != BottomNavScreen.Recipes.route
    BackHandler(enabled = backHandlingEnabled) {
        navController.navigate(BottomNavScreen.Recipes.route) {
            popUpTo(navController.graph.startDestinationId)
            launchSingleTop = true
        }
    }

    val sheetState = rememberModalBottomSheetState()
    val scope = rememberCoroutineScope()
    var showBottomSheet by rememberSaveable { mutableStateOf(false) }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            BottomNavigationBar(navController, items)
        },
        floatingActionButton = {
            if (currentRoute == BottomNavScreen.Recipes.route) {
                if (!sheetState.isVisible) {
                    val glowColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.4f)
                    val gradientBrush = Brush.linearGradient(
                        colors = listOf(
                            MaterialTheme.colorScheme.primary,
                            MaterialTheme.colorScheme.secondaryContainer
                        )
                    )
                    FloatingActionButton(
                        onClick = onChatClicked,
                        modifier = Modifier
                            .offset(x = (-10).dp, y = (-20).dp)
                            .size(64.dp)
                            .drawBehind {
                                drawCircle(
                                    color = glowColor,
                                    radius = 36.dp.toPx(),
                                    center = center
                                )
                            },
                        shape = RoundedCornerShape(20.dp),
                        containerColor = Color.Transparent,
                        elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp, 0.dp, 0.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(gradientBrush, RoundedCornerShape(20.dp)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_ai_spark),
                                contentDescription = "AI Assistant Spark",
                                tint = White,
                                modifier = Modifier.size(30.dp)
                            )
                        }
                    }
                }
            }
        },
        topBar = {
            if (currentRoute == BottomNavScreen.Recipes.route) {
                RecipesTopBar(
                    onSearchClicked = onSearchClicked,
                    isLightMode = isLightMode,
                    onThemeUpdated = onThemeUpdated,
                    scrollBehavior = scrollBehavior,
                    onFilterClicked = {
                        showBottomSheet = true
                        scope.launch { sheetState.show() }
                    }
                )
            }
        },
    ) { innerPadding ->

        if (showBottomSheet) {
            BottomSheetDesign(
                sheetState = sheetState,
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
            val bottomPadding = innerPadding.calculateBottomPadding()
            composable(BottomNavScreen.Recipes.route) {
                RecipesScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    onNavigationClick = { result ->
                        onNavigationClick(result)
                    }
                )
            }
            composable(BottomNavScreen.Favorites.route) {
                FavoritesScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = bottomPadding),
                    onNavigationClick = { result ->
                        onNavigationClick(result)
                    }
                )
            }
            composable(BottomNavScreen.Joke.route) {
                JokeScreen(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(bottom = bottomPadding)
                )
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
    val viewmodel: RecipesViewModel = hiltViewModel()
    val selectedMeal = viewmodel.selectedMealType
    val selectedDiet = viewmodel.selectedDietType

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentSize()
                .padding(start = 16.dp, end = 16.dp, bottom = 32.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.padding(top = 16.dp),
                text = "Meal Type",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.titleColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            MealTypeChipGroup(
                meals = MealType.getMeals(),
                selectedMeal = selectedMeal.value,
                onSelectedChange = { text ->
                    selectedMeal.value = Meal(text)
                }
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Diet Type",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.titleColor
            )
            Spacer(modifier = Modifier.height(8.dp))
            DietTypeChipGroup(
                diets = DietType.getDiets(),
                selectedDiet = selectedDiet.value,
                onSelectedChange = { text ->
                    selectedDiet.value = Diet(text)
                }
            )
            Spacer(modifier = Modifier.height(28.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
            ) {
                Button(
                    modifier = Modifier
                        .fillMaxWidth(0.8f)
                        .height(52.dp),
                    onClick = {
                        viewmodel.getRecipes(selectedMeal.value, selectedDiet.value)
                        onSelect()
                    },
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    )
                ) {
                    Text(
                        text = "Apply Filters",
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
    }
}

@Composable
fun RecipesTopBar(
    onSearchClicked: () -> Unit,
    isLightMode: Boolean,
    onThemeUpdated: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    onFilterClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = "Recipes",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.titleColor
            )
        },
        scrollBehavior = scrollBehavior,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroundColor.copy(alpha = 0.9f)
        ),
        actions = {
            IconButton(onClick = onSearchClicked) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                    tint = MaterialTheme.colorScheme.titleColor
                )
            }
            IconButton(onClick = onThemeUpdated) {
                Icon(
                    painter = if (isLightMode) {
                        painterResource(id = R.drawable.ic_moon)
                    } else {
                        painterResource(id = R.drawable.ic_sun)
                    },
                    contentDescription = "Toggle Dark Mode",
                    tint = MaterialTheme.colorScheme.titleColor
                )
            }
            IconButton(onClick = onFilterClicked) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_restaurant_24),
                    contentDescription = "Filter Recipes",
                    tint = MaterialTheme.colorScheme.titleColor
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

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        items.forEach { screen ->
            val isSelected = currentRoute == screen.route
            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
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
                        text = screen.title.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.labelSmall
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.onPrimary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                    indicatorColor = MaterialTheme.colorScheme.primary
                )
            )
        }
    }
}

@Preview
@Composable
private fun DashBoardScreenPrev() {
    JetFoodRecipeAppMultiModuleTheme {
        DashBoardScreen(
            modifier = Modifier,
            onNavigationClick = {},
            onSearchClicked = {},
            onChatClicked = {},
            onThemeUpdated = {},
            isLightMode = isSystemInDarkTheme()
        )
    }
}
