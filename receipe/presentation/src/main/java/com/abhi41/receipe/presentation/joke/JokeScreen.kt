package com.abhi41.receipe.presentation.joke

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhi41.receipe.domain.models.FoodJoke
import com.abhi41.receipe.presentation.R
import com.abhi41.receipe.ui.theme.EXTRA_LARGE_PADDING
import com.abhi41.receipe.ui.theme.JetFoodRecipeAppMultiModuleTheme
import com.abhi41.receipe.ui.theme.MEDIUM_PADDING
import com.abhi41.receipe.ui.theme.TXT_LARGE_SIZE
import com.abhi41.receipe.ui.theme.txtFoodJoke

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JokeScreen(
    modifier: Modifier = Modifier,
    foodJokeViewModel: FoodJokeViewModel = hiltViewModel()
) {
    Scaffold(
        topBar = {
            JokeTopBar()
        },
        content = { innerPadding ->
            val topPadding = innerPadding.calculateTopPadding()

            val state by foodJokeViewModel.foodJokeState

            if (state.isLoading) {
                Box(modifier = Modifier.fillMaxSize()) {
                    showLoader(
                        modifier,
                        topPadding
                    )
                }
            } else if (state.foodJoke.isNotEmpty()) {
                   foodJokeDesign(modifier, topPadding, state.foodJoke) {//call api on card click listener
                    foodJokeViewModel.getFoodJoke()
                }
            }

        }
    )
}

@Composable
private fun foodJokeDesign(
    modifier: Modifier,
    topPadding: Dp,
    foodJoke: List<FoodJoke>,
    callJokeApi: () -> Unit
) {
    val painter = if (isSystemInDarkTheme())
        painterResource(id = R.drawable.ic_food_joke_background_dark)
    else painterResource(id = R.drawable.ic_food_joke_background)
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(
                top = topPadding
            )
    ) {
        Image(
            painter = painter,
            contentScale = ContentScale.Crop,
            contentDescription = "food joke background",
            modifier = Modifier.fillMaxSize()
        )
        Card(
            modifier = Modifier
                .clickable {
                    callJokeApi()
                }
                .wrapContentHeight()
                .padding(EXTRA_LARGE_PADDING)
                .background(Color.Transparent)
                .border(
                    1.dp, Color.White, shape = RoundedCornerShape(
                        size = MEDIUM_PADDING
                    )
                ),
        ) {
            Box(
                modifier = Modifier
                    .padding(MEDIUM_PADDING)
                    .verticalScroll(rememberScrollState()),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = foodJoke.get(0).text ?: "",
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.txtFoodJoke,
                    fontFamily = FontFamily.Monospace,
                    fontSize = TXT_LARGE_SIZE
                )
            }
        }
    }
}

@Composable
private fun showLoader(
    modifier: Modifier,
    topPadding: Dp,
) {
    val painter = if (isSystemInDarkTheme())
        painterResource(id = R.drawable.ic_food_joke_background_dark)
    else painterResource(id = R.drawable.ic_food_joke_background)
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .padding(
                top = topPadding
            )
    ) {
        Image(
            painter = painter,
            contentScale = ContentScale.Crop,
            contentDescription = "food joke background",
            modifier = Modifier.fillMaxSize()
        )
        CircularProgressIndicator()
    }
}

@Preview(showBackground = true)
@Composable
fun JokeScreenPreview() {
    JetFoodRecipeAppMultiModuleTheme {
        JokeScreen()
    }
}