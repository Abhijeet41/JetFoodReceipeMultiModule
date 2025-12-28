package com.abhi41.receipe.presentation.recipes

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.rememberAsyncImagePainter
import com.abhi41.receipe.domain.models.Result
import com.abhi41.receipe.presentation.R
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
    modifier: Modifier
) {
    val viewModel: RecipesViewModel = hiltViewModel()
    val recipesState = viewModel.recipesState.value

    LaunchedEffect(key1 = Unit) {
        viewModel.getRecipes()
    }

    if (recipesState.isLoading) {
        Box(modifier = Modifier.fillMaxSize()) {
            CircularProgressIndicator()
        }
    } else if (recipesState.recipesItem.isNullOrEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = "No Recipes Found",
                modifier = Modifier.align(Alignment.Center),
            )
        }
    } else if (recipesState.recipesItem.isNotEmpty()) {
        RecipeDesignContent(modifier, recipesState.recipesItem)
    }

}

@Composable
fun RecipeDesignContent(modifier: Modifier, recipesItem: List<Result>) {
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
            RecipeItem(it)
        }
    }
}

@Composable
fun RecipeItem(item: Result) {
    val foodImage = rememberAsyncImagePainter(model = item.image, error = painterResource(id = R.drawable.ic_error_placeholder))
    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                MaterialTheme.colorScheme.cardStrokeBorder,
                shape = RoundedCornerShape(size = MEDIUM_PADDING)
            )
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
                        text = Jsoup.parse(item.summary).text(),
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
fun HorizontalLikesAndCategory(item: Result) {
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
            text = "${item.readyInMinutes}",
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

@Preview(showBackground = true)
@Composable
private fun RecipeItemPrev() {
    RecipeItem(
        Result(
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
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RecipeDesignContentPrew() {
    RecipeDesignContent(
        Modifier.fillMaxSize(),
        recipesItem = listOf(
            Result(
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
    )
}