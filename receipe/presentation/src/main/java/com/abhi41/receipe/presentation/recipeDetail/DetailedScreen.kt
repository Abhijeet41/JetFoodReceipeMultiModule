package com.abhi41.receipe.presentation.recipeDetail

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.wear.compose.material.ContentAlpha
import com.abhi41.receipe.domain.models.RecipeResult
import com.abhi41.receipe.presentation.recipeDetail.tabs.IngredientsScreen
import com.abhi41.receipe.presentation.recipeDetail.tabs.InstructionScreen
import com.abhi41.receipe.presentation.recipeDetail.tabs.OverviewScreen
import com.abhi41.receipe.ui.theme.tabBackgroundColor
import kotlinx.coroutines.launch

private const val TAG = "DetailedScreen"

@Composable
fun DetailedScreen(
    modifier: Modifier = Modifier,
    recipeResult: RecipeResult,
    onBackClicked: () -> Unit
) {
    var result by remember { mutableStateOf<RecipeResult?>(null) }
    val tabItems = listOf("Overview", "Ingredients", "Instruction")
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f,
        pageCount = {
            tabItems.size
        }
    )
    val coroutineScope = rememberCoroutineScope()
    // --- THIS IS THE FIX ---
    // 3. Add the BackHandler composable
    BackHandler(enabled = true) {
        if (pagerState.currentPage > 0) {
            // If the user is on "Ingredients" or "Instruction", go back to "Overview"
            coroutineScope.launch {
                pagerState.animateScrollToPage(0)
            }
        }else {
            // If the user is already on "Overview", perform the normal back action
            onBackClicked()
        }
    }
    LaunchedEffect(key1 = Unit) {
        result = recipeResult
        Log.d(TAG, "DetailedScreen: ${recipeResult.recipeId} ${recipeResult.image}")
    }
    Scaffold (
        topBar = {
            DetailedScreenAppBar(
                onBackArrowClicked = {
                    onBackClicked()
                },
                onFavoriteClicked = {

                }
            )
        }
    ){ innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerPadding)
        ) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground,
                divider = {
                    HorizontalDivider(
                        thickness = 1.dp,
                        color = Color.Transparent
                    )
                },
                indicator = { tabPositions ->
                    if (pagerState.currentPage < tabPositions.size) {
                        TabRowDefaults.SecondaryIndicator(
                            modifier = Modifier
                                .tabIndicatorOffset(
                                    tabPositions[pagerState.currentPage]
                                ),
                            height = 3.dp,
                            color = Color.White
                        )
                    }
                }
            ) {
                tabItems.forEachIndexed { index, title ->
                    val selected = pagerState.currentPage == index

                    Tab(
                        modifier = Modifier.background(
                            color = MaterialTheme.colorScheme.tabBackgroundColor,
                        ),
                        text = {
                            Text(
                                text = title,
                                style = TextStyle(
                                    color = if (selected) Color.White else Color.White.copy(
                                        alpha = ContentAlpha.disabled
                                    ),
                                    fontSize = 15.sp
                                )
                            )
                        },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            coroutineScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
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

@Composable
fun HorizontalPagerCompose(
    pagerState: PagerState,
    selectedFoodItem: RecipeResult
) {
    HorizontalPager(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        state = pagerState,
        userScrollEnabled = false, // Apply the dynamic scroll state here
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