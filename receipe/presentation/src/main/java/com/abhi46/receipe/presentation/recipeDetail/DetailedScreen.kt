package com.abhi46.receipe.presentation.recipeDetail

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhi46.receipe.data.mappers.toFavoriteEntity
import com.abhi46.receipe.domain.models.RecipeResult
import com.abhi46.receipe.presentation.recipeDetail.tabs.IngredientsScreen
import com.abhi46.receipe.presentation.recipeDetail.tabs.InstructionScreen
import com.abhi46.receipe.presentation.recipeDetail.tabs.overview.OverviewScreen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

private const val TAG = "DetailedScreen"

@Composable
fun DetailedScreen(
    recipeResult: RecipeResult,
    detailViewModel: DetailViewModel = hiltViewModel(),
    onBackClicked: () -> Unit,
) {
    val favoritesRecipes = detailViewModel.readFavoriteRecipes.observeAsState(emptyList())
    var result by remember { mutableStateOf<RecipeResult?>(null) }
    val tabItems = listOf("Overview", "Ingredients", "Instructions")
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = { tabItems.size }
    )
    val coroutineScope = rememberCoroutineScope()

    val backAction = rememberBackAction(pagerState, coroutineScope, onBackClicked)

    BackHandler(enabled = true) {
        backAction()
    }

    LaunchedEffect(key1 = Unit) {
        result = recipeResult
        Log.d(TAG, "DetailedScreen LaunchedEffect: ${recipeResult.recipeId} ${recipeResult.image}")
    }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            DetailedScreenAppBar(
                favoritesRecipes = favoritesRecipes.value,
                selectedRecipe = recipeResult,
                onBackArrowClicked = { backAction() },
                onFavoriteClicked = { isRecipeSaved ->
                    handleFavoriteClick(isRecipeSaved, detailViewModel, recipeResult)
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.primary,
                divider = {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f)
                    )
                },
                indicator = { tabPositions ->
                    if (pagerState.currentPage < tabPositions.size) {
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier.tabIndicatorOffset(
                                tabPositions[pagerState.currentPage]
                            ),
                            height = 3.dp,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            ) {
                tabItems.forEachIndexed { index, title ->
                    val selected = pagerState.currentPage == index

                    Tab(
                        modifier = Modifier.background(MaterialTheme.colorScheme.surface),
                        selected = selected,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        },
                        text = {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (selected) FontWeight.Bold else FontWeight.Medium,
                                color = if (selected) {
                                    MaterialTheme.colorScheme.primary
                                } else {
                                    MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f)
                                }
                            )
                        }
                    )
                }
            }
            result?.let { recipeResult ->
                HorizontalPagerCompose(
                    pagerState = pagerState,
                    selectedFoodItem = recipeResult
                )
            }
        }
    }
}

private fun handleFavoriteClick(
    isRecipeSaved: Boolean,
    detailViewModel: DetailViewModel,
    recipeResult: RecipeResult
) {
    if (isRecipeSaved) {
        detailViewModel.deleteFavoriteRecipeById(recipeResult.recipeId)
    } else {
        detailViewModel.insertFavoriteRecipes(recipes = recipeResult.toFavoriteEntity())
    }
}

@Composable
private fun rememberBackAction(
    pagerState: PagerState,
    coroutineScope: CoroutineScope,
    onBackClicked: () -> Unit
): () -> Unit {
    return remember(pagerState) {
        {
            if (pagerState.currentPage > 0) {
                coroutineScope.launch {
                    pagerState.animateScrollToPage(0)
                }
            } else {
                onBackClicked()
            }
        }
    }
}

@Composable
fun HorizontalPagerCompose(
    pagerState: PagerState,
    selectedFoodItem: RecipeResult
) {
    HorizontalPager(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        state = pagerState,
        userScrollEnabled = false,
        beyondViewportPageCount = 1
    ) { page ->
        when (page) {
            0 -> {
                OverviewScreen(selectedFoodItem)
            }
            1 -> {
                IngredientsScreen(selectedFoodItem.extendedIngredients)
            }
            2 -> {
                InstructionScreen(selectedFoodItem.sourceUrl)
            }
        }
    }
}
