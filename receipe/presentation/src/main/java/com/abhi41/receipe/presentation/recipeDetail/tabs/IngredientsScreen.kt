package com.abhi41.receipe.presentation.recipeDetail.tabs

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest.Builder
import com.abhi41.receipe.domain.models.ExtendedIngredien
import com.abhi41.receipe.presentation.R
import com.abhi41.receipe.presentation.utils.Constants
import com.abhi41.receipe.ui.theme.MEDIUM_PADDING
import com.abhi41.receipe.ui.theme.SMALL_PADDING
import com.abhi41.receipe.ui.theme.TXT_TITLE_TEXT
import com.abhi41.receipe.ui.theme.cardStrokeBorder
import com.abhi41.receipe.ui.theme.titleColor

@Composable
fun IngredientsScreen(extendedIngredients: List<ExtendedIngredien>?) {

    Scaffold {innerpadding->
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(innerpadding),
            contentPadding = PaddingValues(all = SMALL_PADDING),
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            items(extendedIngredients?.size ?: 0) { index ->
                extendedIngredients?.let { IngredientItem(it[index]) }
            }
        }
    }

}

@Composable
fun IngredientItem(ingredient: ExtendedIngredien) {
    val painter =
        rememberAsyncImagePainter(
            Builder(LocalContext.current).data(data = "${Constants.BASE_IMAGE_URL}${ingredient.image}")
                .apply { ->
                    placeholder(R.drawable.ic_placeholder)
                    crossfade(600)
                    error(R.drawable.ic_error_placeholder)
                }.build()
        )

    Box(
        modifier = Modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.cardStrokeBorder,
                shape = RoundedCornerShape(
                    size = MEDIUM_PADDING
                )
            )
            .wrapContentHeight()
            .clickable {},
    ) {

        Surface(
            modifier = Modifier.padding(MEDIUM_PADDING),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                horizontalArrangement = Arrangement.Start,
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth(0.4f)
                        .background(Color.Transparent)
                        //.wrapContentHeight(),
                        .height(150.dp),
                    painter = painter,
                    contentDescription = "Ingredient Image",
                    contentScale = ContentScale.Crop
                )
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .padding(SMALL_PADDING),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ) {
                    Text(
                        text = ingredient.name,
                        style = MaterialTheme.typography.bodyMedium,
                        fontSize = TXT_TITLE_TEXT,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.titleColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.padding(
                            top = SMALL_PADDING,
                        ),
                        text = ingredient.amount.toString(),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.titleColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.padding(
                            top = SMALL_PADDING,
                        ),
                        text = ingredient.consistency,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.titleColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        modifier = Modifier.padding(
                            top = SMALL_PADDING,
                        ),
                        text = ingredient.original,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.titleColor,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

            }
        }

    }

}


@Preview(showBackground = true, showSystemUi = true )
@Composable
private fun IngredientsScreenPrev() {
    val mockIngredient = ExtendedIngredien(
        amount = 2.0,
        consistency = "SOLID",
        image = "flour.png", // Just the image name, as your code adds the base URL
        name = "All-purpose flour",
        original = "2 cups all-purpose flour",
        unit = "cups"
    )
    IngredientsScreen(listOf(mockIngredient))
}

@Preview(showBackground = true)
@Composable
private fun IngredientItemPreview() {
    // 1. Create a mock 'ExtendedIngredien' object to simulate real data.
    val mockIngredient = ExtendedIngredien(
        amount = 2.0,
        consistency = "SOLID",
        image = "flour.png", // Just the image name, as your code adds the base URL
        name = "All-purpose flour",
        original = "2 cups all-purpose flour",
        unit = "cups"
    )

    // 2. Call your IngredientItem composable with the mock data.
    IngredientItem(ingredient = mockIngredient)
}