package com.abhi41.receipe.presentation.recipes

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.abhi41.receipe.domain.models.RecipeResult
import com.abhi41.receipe.presentation.R
import com.abhi41.receipe.presentation.utils.Common
import com.abhi41.receipe.ui.theme.EXTRA_SMALL_PADDING
import com.abhi41.receipe.ui.theme.FoodRecipe_ITEM_HEIGHT
import com.abhi41.receipe.ui.theme.MEDIUM_PADDING
import com.abhi41.receipe.ui.theme.SMALL_PADDING
import com.abhi41.receipe.ui.theme.cardStrokeBorder
import com.abhi41.receipe.ui.theme.descriptionColor
import com.abhi41.receipe.ui.theme.readyInMinute
import com.abhi41.receipe.ui.theme.titleColor
import org.jsoup.Jsoup

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(
    modifier: Modifier,
    onNavigationClick: (RecipeResult) -> Unit
) {
    val viewModel: RecipesViewModel = hiltViewModel()
    val recipesState by viewModel.recipesState

    if (recipesState.isLoading) {
        /* Box(modifier = Modifier.fillMaxSize()) {
             CircularProgressIndicator()
         }*/
        AnimatedShimmer()
    } else if (!recipesState.error.equals("")) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "No Recipes Found",
                modifier = Modifier.align(Alignment.Center),
            )
        }
    } else if (recipesState.recipesItem.isNotEmpty()) {
        RecipeDesignContent(modifier, recipesState.recipesItem, { result ->
            viewModel.logRecipeClickedEvent(
                recipeId = result.recipeId.toString(),
                recipeName = result.title,
                screenName = "Recipes Screen",
            )
            viewModel.trackScreen()
            onNavigationClick(result)
        })
    }

}

@Composable
fun RecipeDesignContent(
    modifier: Modifier,
    recipesItem: List<RecipeResult>,
    onNavigationClick: (RecipeResult) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(SMALL_PADDING),
        verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
    ) {
        /*   items(
               count = 10,
               key = {
                   it
               }
           ){
               RecipeItem()
           }*/
        items(
            items = recipesItem,
            key = {
                it.recipeId
            }
        ) {
            RecipeItem(
                it, { result ->
                    onNavigationClick(result)
                }
            )
        }
    }
}

@Composable
fun RecipeItem(item: RecipeResult, onNavigationClick: (RecipeResult) -> Unit) {
    val foodImage = rememberAsyncImagePainter(
        model = item.image,
        error = painterResource(id = R.drawable.ic_error_placeholder)
    )
    val cleanSummary = Jsoup.parse(item.summary).text()

    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                MaterialTheme.colorScheme.cardStrokeBorder,
                shape = RoundedCornerShape(size = MEDIUM_PADDING)
            )
            .clickable {
                onNavigationClick(item)
            }
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
                        text = item.title,
                        color = MaterialTheme.colorScheme.titleColor,
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(Modifier.height(10.dp))
                    Text(
                        text = cleanSummary,
                        color = MaterialTheme.colorScheme.descriptionColor,
                        style = MaterialTheme.typography.bodySmall,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 4
                    )
                    HorizontalLikesAndCategory(item)
                }


            }

        }

    }

}

@Composable
fun HorizontalLikesAndCategory(item: RecipeResult) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = SMALL_PADDING)
    ) {
        InfoColumn(
            text = "${item.aggregateLikes}",
            icon = R.drawable.ic_heart,
            color = Color.Red
        )
        InfoColumn(
            text = if (item.readyInMinutes > 60) "${Common.convertMinutesInHour(item.readyInMinutes)}Hr" else "${item.readyInMinutes} Min",
            icon = R.drawable.ic_clock,
            color = MaterialTheme.colorScheme.readyInMinute
        )
        InfoColumn(
            text = if (item.vegan) "Vegan" else "Non Vegan",
            icon = R.drawable.ic_leaf,
            color = Color.Green
        )

    }
}

@Composable
private fun InfoColumn(icon: Int, text: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(id = icon),
            contentDescription = "",
            tint = if (text.equals("Non Vegan")) Color.Red else color
        )

        Text(
            text = text,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            color = if (text.equals("Non Vegan")) Color.Red else color
        )
    }
}

@Composable
fun AnimatedShimmer(modifier: Modifier = Modifier) {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.6f),
        Color.LightGray.copy(alpha = 0.2f),
        Color.LightGray.copy(alpha = 0.6f),
    )
    val transition = rememberInfiniteTransition()
    val translateAnimation = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = FastOutSlowInEasing
            )
        )
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, translateAnimation.value)
    )

    Column(
        modifier = modifier
    ) {
        repeat(6) {
            ShimmerListItem(brush = brush)
        }
    }
}

@Composable
fun ShimmerListItem(brush: Brush) {
    Box(
        modifier = Modifier
            .border(
                1.dp, MaterialTheme.colorScheme.cardStrokeBorder, shape = RoundedCornerShape(
                    size = MEDIUM_PADDING
                )
            )
            .height(FoodRecipe_ITEM_HEIGHT)
    ) {
        Surface(
            shape = RoundedCornerShape(
                size = MEDIUM_PADDING
            )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(),
                // verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                        .padding(EXTRA_SMALL_PADDING)
                        .background(brush)
                        .fillMaxHeight(),
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(EXTRA_SMALL_PADDING)
                ) {
                    Spacer(
                        modifier = Modifier
                            .background(brush)
                            .height(30.dp)
                            .padding(EXTRA_SMALL_PADDING)
                            .fillMaxWidth(),
                    )
                    Spacer(Modifier.height(20.dp))
                    Spacer(
                        modifier = Modifier
                            .background(brush)
                            .height(55.dp)
                            .padding(EXTRA_SMALL_PADDING)
                            .fillMaxWidth(),
                    )

                    Row(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        repeat(3) {
                            Spacer(
                                modifier = Modifier
                                    .size(30.dp)
                                    .background(brush)
                            )
                        }
                    }
                    Row(
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        repeat(3) {
                            Spacer(
                                modifier = Modifier
                                    .width(30.dp)
                                    .height(20.dp)
                                    .background(brush)
                            )
                        }

                    }

                }

            }
        }
    }
}


@Preview(showBackground = true)
@Composable
private fun RecipeItemPrev() {
    RecipeItem(
        RecipeResult(
            aggregateLikes = 1,
            cheap = true,
            dairyFree = true,
            extendedIngredients = emptyList(),
            glutenFree = true,
            recipeId = 1,
            image = "",
            readyInMinutes = 1,
            sourceName = "",
            sourceUrl = "",
            summary = "",
            title = "",
            vegan = true,
            vegetarian = true,
            veryHealthy = true

        )
    ) {}
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RecipeDesignContentPrew() {
    RecipeDesignContent(
        Modifier.fillMaxSize(),
        recipesItem = listOf(
            RecipeResult(
                aggregateLikes = 1,
                cheap = true,
                dairyFree = true,
                extendedIngredients = emptyList(),
                glutenFree = true,
                recipeId = 1,
                image = "",
                readyInMinutes = 1,
                sourceName = "",
                sourceUrl = "",
                summary = "",
                title = "",
                vegan = true,
                vegetarian = true,
                veryHealthy = true
            )
        )
    ) {

    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun AnimatedShimmerPrev() {
    AnimatedShimmer()
}