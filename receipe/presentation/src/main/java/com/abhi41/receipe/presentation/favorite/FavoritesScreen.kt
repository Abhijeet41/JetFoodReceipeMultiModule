package com.abhi41.receipe.presentation.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.abhi41.receipe.data.mappers.toRecipeResult
import com.abhi41.receipe.domain.models.RecipeResult
import com.abhi41.receipe.presentation.R
import com.abhi41.receipe.presentation.common.BackPressHandler
import com.abhi41.receipe.presentation.recipes.HorizontalLikesAndCategory
import com.abhi41.receipe.ui.theme.FoodRecipe_ITEM_HEIGHT
import com.abhi41.receipe.ui.theme.MEDIUM_PADDING
import com.abhi41.receipe.ui.theme.SMALL_PADDING
import com.abhi41.receipe.ui.theme.cardStrokeBorder
import com.abhi41.receipe.ui.theme.descriptionColor
import com.abhi41.receipe.ui.theme.strokeBorderColor
import com.abhi41.receipe.ui.theme.titleColor
import com.abhi41.recipe.core_database.entity.FavoriteEntity
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
    val favoriteRecipe by viewModel.readFavoriteRecipes.observeAsState()
    var state = remember { mutableStateOf(FavoriteState()) }
    val coroutineScope = rememberCoroutineScope()

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
        topBar = {
            FavoriteTopBar(
                viewModel = viewModel,
                state = state
            ) { //On delete Icon Click delete all selected items

                //after deleting items then show default appbar and hide contextual appbar
                state.value = state.value.copy(
                    isContextual = false,
                    actionModeTitle = "",
                    multiSelection = false
                )

                //here we are deleting all selected items from database
                selectedRecipes.forEach { recipe ->
                    coroutineScope.launch(Dispatchers.IO) {
                        viewModel.deleteFavoriteRecipe(recipe)
                    }
                }

                //after deleting all selected items clear the list
                selectedRecipes.clear()

            }
        }
    ) { innerPadding ->

        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            items(
                items = favoriteRecipe ?: emptyList(),
                key = {
                    it.id
                },
            ) { result ->
                //this will make sure selectedRecipes border color will gets change
                val isSelected = selectedRecipes.contains(result)
              val color =  if (isSelected)  MaterialTheme.colorScheme.strokeBorderColor
                           else MaterialTheme.colorScheme.cardStrokeBorder

                FavoriteFoodItem(result, color, onNavigationClick = {//handle single click event
                    if (state.value.isContextual || state.value.multiSelection) {
                        applicationSelection(
                            currentRecipe = result,
                            state
                        )
                    } else {
                        onNavigationClick(result.toRecipeResult())
                    }
                }) { //handle long click event

                    //if multiSelection is false then enabled it true and show contextual appbar
                    if (!state.value.multiSelection) {
                        state.value = state.value.copy(
                            isContextual = true,
                            multiSelection = true
                        )
                        applicationSelection(result, state)
                    }

                }
            }
        }
    }
}

@Composable
fun FavoriteFoodItem(
    result: FavoriteEntity,
    color: Color,
    onNavigationClick: (RecipeResult) -> Unit,
    onLongClick: () -> Unit
) {
    val foodImage = rememberAsyncImagePainter(
        model = result.image,
        error = painterResource(id = R.drawable.ic_error_placeholder)
    )
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color,
                shape = RoundedCornerShape(size = MEDIUM_PADDING)
            )
            .combinedClickable(
                onClick = {
                    onNavigationClick(result.toRecipeResult())
                },
                onLongClick = {
                    onLongClick()
                }
            )
            /* .clickable {
                 onNavigationClick(result.toRecipeResult())
             }*/
            .height(FoodRecipe_ITEM_HEIGHT)

    ) {
        Surface(
            shape = RoundedCornerShape(size = MEDIUM_PADDING),
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .fillMaxHeight(),
                    painter = foodImage,
                    contentScale = ContentScale.Crop,
                    contentDescription = ""
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(SMALL_PADDING)
                ) {
                    Text(
                        text = result.title,
                        color = MaterialTheme.colorScheme.titleColor,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = Jsoup.parse(result.summary).text(),
                        color = MaterialTheme.colorScheme.descriptionColor,
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 4
                    )
                    HorizontalLikesAndCategory(result.toRecipeResult())
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
    // We don't have a real ViewModel in previews, so we pass a default one.
    // The onNavigationClick can be an empty lambda as it won't be used in the preview.
    FavoritesScreen {}
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
        onLongClick = { /* Long clicks are disabled in preview */ }
    )
}

