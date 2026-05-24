package com.abhi46.receipe.presentation.favorite

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import com.abhi46.receipe.data.mappers.toRecipeResult
import com.abhi46.receipe.domain.models.RecipeResult
import com.abhi46.receipe.presentation.R
import com.abhi46.receipe.presentation.common.BackPressHandler
import com.abhi46.receipe.presentation.utils.Common
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.ui.text.font.FontWeight
import com.abhi46.receipe.ui.theme.JetFoodRecipeAppMultiModuleTheme
import com.abhi46.receipe.ui.theme.MEDIUM_PADDING
import com.abhi46.receipe.ui.theme.SMALL_PADDING
import com.abhi46.receipe.ui.theme.cardStrokeBorder
import com.abhi46.receipe.ui.theme.readyInMinute
import com.abhi46.receipe.ui.theme.categoriesSelectedIconColor
import com.abhi46.receipe.ui.theme.descriptionColor
import com.abhi46.receipe.ui.theme.strokeBorderColor
import com.abhi46.receipe.ui.theme.titleColor
import com.abhi46.recipe.core_database.entity.FavoriteEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup

private const val TAG = "FavoritesScreen"
var selectedRecipes: MutableList<FavoriteEntity> = mutableListOf()

@Composable
fun FavoritesScreen(
    modifier: Modifier = Modifier,
    viewModel: FavoritesViewModel = hiltViewModel(),
    onNavigationClick: (RecipeResult) -> Unit
) {
    val favoriteRecipe by viewModel.readFavoriteRecipes.observeAsState(emptyList())
    val coroutineScope = rememberCoroutineScope()

    FavoritesContent(
        modifier = modifier,
        favoriteRecipes = favoriteRecipe,
        imageLoader = viewModel.imageLoader,
        onNavigationClick = onNavigationClick,
        onDeleteFavoriteRecipe = { recipe ->
            coroutineScope.launch(Dispatchers.IO) {
                viewModel.deleteFavoriteRecipe(recipe)
            }
        },
        onInsertFavoriteRecipe = { recipe ->
            coroutineScope.launch(Dispatchers.IO) {
                viewModel.insertFavoriteRecipe(recipe)
            }
        },
        onDeleteAllFavoriteRecipes = {
            coroutineScope.launch {
                viewModel.deleteAllFavoriteRecipes()
            }
        }
    )
}

@Composable
fun FavoritesContent(
    modifier: Modifier = Modifier,
    favoriteRecipes: List<FavoriteEntity>,
    imageLoader: ImageLoader,
    onNavigationClick: (RecipeResult) -> Unit,
    onDeleteFavoriteRecipe: (FavoriteEntity) -> Unit,
    onInsertFavoriteRecipe: (FavoriteEntity) -> Unit,
    onDeleteAllFavoriteRecipes: () -> Unit
) {
    var state = remember { mutableStateOf(FavoriteState()) }
    val coroutineScope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    val onBack = { //handle on back pressed
        state.value = state.value.copy(
            isContextual = false,
            actionModeTitle = "",
            multiSelection = false
        )
        selectedRecipes.clear()
    }
    if (state.value.isContextual) {
        BackPressHandler(onBackPressed = onBack)
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            FavoriteTopBar(
                state = state,
                onDeleteClick = { //On delete Icon Click delete all selected items
                    //after deleting items then show default appbar and hide contextual appbar
                    state.value = state.value.copy(
                        isContextual = false,
                        actionModeTitle = "",
                        multiSelection = false
                    )

                    //here we are deleting all selected items from database
                    selectedRecipes.forEach { recipe ->
                        onDeleteFavoriteRecipe(recipe)
                    }

                    //after deleting all selected items clear the list
                    selectedRecipes.clear()
                },
                onDeleteAllClick = onDeleteAllFavoriteRecipes
            )
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            contentPadding = PaddingValues(SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            items(
                items = favoriteRecipes,
                key = {
                    it.id
                },
            ) { result ->
                //this will make sure selectedRecipes border color will gets change
                val isSelected = selectedRecipes.contains(result)
                val color = if (isSelected) MaterialTheme.colorScheme.strokeBorderColor
                else MaterialTheme.colorScheme.cardStrokeBorder

                val dismissState = rememberSwipeToDismissBoxState(
                    confirmValueChange = { dismissValue ->
                        if (dismissValue == SwipeToDismissBoxValue.EndToStart) {
                            onDeleteFavoriteRecipe(result)
                            coroutineScope.launch {
                                val snackbarResult = snackbarHostState.showSnackbar(
                                    message = "Recipe deleted",
                                    actionLabel = "UNDO",
                                    duration = SnackbarDuration.Short
                                )
                                if (snackbarResult == SnackbarResult.ActionPerformed) {
                                    onInsertFavoriteRecipe(result)
                                }
                            }
                            true
                        } else {
                            false
                        }
                    }
                )

                SwipeToDismissBox(
                    state = dismissState,
                    enableDismissFromStartToEnd = false,
                    enableDismissFromEndToStart = true,
                    backgroundContent = {
                        val bgColor by animateColorAsState(
                            targetValue = when (dismissState.targetValue) {
                                SwipeToDismissBoxValue.Settled -> Color.Transparent
                                else -> Color.Red
                            }
                        )
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(bgColor, RoundedCornerShape(size = MEDIUM_PADDING)),
                            contentAlignment = Alignment.CenterEnd
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete",
                                tint = Color.White,
                                modifier = Modifier.padding(end = 24.dp)
                            )
                        }
                    },
                    content = {
                        FavoriteFoodItem(
                            result = result,
                            color = color,
                            imageLoader = imageLoader,
                            onNavigationClick = {//handle single click event
                                if (state.value.isContextual || state.value.multiSelection) {
                                    applicationSelection(
                                        currentRecipe = result,
                                        state
                                    )
                                } else {
                                    onNavigationClick(result.toRecipeResult())
                                }
                            },
                            onLongClick = {
                                //handle long click event
        
                                //if multiSelection is false then enabled it true and show contextual appbar
                                if (!state.value.multiSelection) {
                                    state.value = state.value.copy(
                                        isContextual = true,
                                        multiSelection = true
                                    )
                                    applicationSelection(result, state)
                                }
                            })
                    }
                )
            }
        }
    }
}

@Composable
fun FavoriteFoodItem(
    result: FavoriteEntity,
    color: Color,
    onNavigationClick: (RecipeResult) -> Unit,
    onLongClick: () -> Unit,
    imageLoader: ImageLoader
) {
    val cleanSummary = Jsoup.parse(result.summary).text()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .combinedClickable(
                onClick = { onNavigationClick(result.toRecipeResult()) },
                onLongClick = onLongClick
            )
            .border(
                width = 1.dp,
                color = color,
                shape = RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = result.image,
                    imageLoader = imageLoader,
                    contentDescription = result.title,
                    placeholder = painterResource(id = R.drawable.ic_placeholder),
                    error = painterResource(id = R.drawable.ic_error_placeholder),
                    contentScale = ContentScale.Crop
                )

                // Overlaid Vegan / Non-vegan Badge
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(16.dp)
                ) {
                    val badgeColor = if (result.vegan) {
                        MaterialTheme.colorScheme.categoriesSelectedIconColor.copy(alpha = 0.9f)
                    } else {
                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.9f)
                    }
                    val badgeText = if (result.vegan) "Vegan" else "Non-vegan"
                    val badgeIcon = if (result.vegan) R.drawable.ic_leaf else R.drawable.ic_restaurant_24

                    Surface(
                        shape = RoundedCornerShape(12.dp),
                        color = badgeColor,
                        modifier = Modifier.wrapContentSize()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = badgeIcon),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = badgeText,
                                color = Color.White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Text(
                    text = result.title,
                    color = MaterialTheme.colorScheme.titleColor,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = cleanSummary,
                    color = MaterialTheme.colorScheme.descriptionColor,
                    style = MaterialTheme.typography.bodyMedium,
                    overflow = TextOverflow.Ellipsis,
                    maxLines = 2,
                    lineHeight = 20.sp
                )
                Spacer(modifier = Modifier.height(16.dp))
                HorizontalDivider(
                    color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.2f),
                    thickness = 1.dp
                )
                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_clock),
                            contentDescription = "Cook Time",
                            tint = MaterialTheme.colorScheme.readyInMinute,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = if (result.readyInMinutes > 60) {
                                "${Common.convertMinutesInHour(result.readyInMinutes)} Hr"
                            } else {
                                "${result.readyInMinutes} Min"
                            },
                            color = MaterialTheme.colorScheme.readyInMinute,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_heart),
                            contentDescription = "Likes count",
                            tint = Color.Red,
                            modifier = Modifier.size(18.dp)
                        )
                        Text(
                            text = "${result.aggregateLikes}",
                            color = MaterialTheme.colorScheme.titleColor,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }
    }
}

fun applicationSelection(
    currentRecipe: FavoriteEntity,
    state: MutableState<FavoriteState>,
) {
    if (selectedRecipes.contains(currentRecipe)) {
        selectedRecipes.remove(currentRecipe)
        applyActionModeTitle(state = state)
    } else {
        selectedRecipes.add(currentRecipe)
        applyActionModeTitle(state = state)
    }
}

fun applyActionModeTitle(state: MutableState<FavoriteState>) {
    when (selectedRecipes.size) {
        0 -> {
            state.value = state.value.copy(
                isContextual = false,
                actionModeTitle = "",
                multiSelection = false
            )
        }

        1 -> {
            state.value =
                state.value.copy(actionModeTitle = "${selectedRecipes.size} item selected")
        }

        else -> {
            state.value =
                state.value.copy(actionModeTitle = "${selectedRecipes.size} items selected")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteScreenPreview() {
    JetFoodRecipeAppMultiModuleTheme {
        FavoritesContent(
            favoriteRecipes = listOf(
                FavoriteEntity(
                    id = 1,
                    recipeId = 641803,
                    aggregateLikes = 25,
                    image = "",
                    readyInMinutes = 45,
                    sourceUrl = "",
                    summary = "Mock summary for preview.",
                    title = "Mock Recipe",
                    vegan = true,
                    vegetarian = true,
                    veryHealthy = true,
                    cheap = false,
                    dairyFree = true,
                    glutenFree = true,
                    sourceName = "Mock Source",
                    extendedIngredients = emptyList()
                )
            ),
            imageLoader = ImageLoader.Builder(LocalContext.current).build(),
            onNavigationClick = {},
            onDeleteFavoriteRecipe = {},
            onInsertFavoriteRecipe = {},
            onDeleteAllFavoriteRecipes = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun FavoriteFoodItemPreview() {
    // 1. Create a mock 'FavoriteEntity' object to simulate real data.
    val mockFavorite = FavoriteEntity(
        id = 1,
        recipeId = 641803,
        aggregateLikes = 25,
        image = "https://spoonacular.com/recipeImages/641803-312x231.jpg",
        readyInMinutes = 45,
        sourceUrl = "http://www.foodista.com/recipe/52G86T2N/dutch-oven-bread",
        summary = "Dutch Oven Bread is a classic recipe that is perfect for any occasion. This bread is soft, fluffy, and has a crispy crust. It is perfect for sandwiches, toast, or just eating on its own.",
        title = "Dutch Oven Bread",
        vegan = false,
        vegetarian = true,
        veryHealthy = false,
        cheap = false,
        dairyFree = false,
        glutenFree = false,
        sourceName = "Foodista",
        extendedIngredients = emptyList()
    )

    // 2. Call your FavoriteFoodItem composable with the mock data.
    FavoriteFoodItem(
        result = mockFavorite,
        color = MaterialTheme.colorScheme.cardStrokeBorder,
        onNavigationClick = { /* Clicks are disabled in preview */ },
        onLongClick = { /* Long clicks are disabled in preview */ },
        imageLoader = ImageLoader.Builder(LocalContext.current).build()
    )
}
