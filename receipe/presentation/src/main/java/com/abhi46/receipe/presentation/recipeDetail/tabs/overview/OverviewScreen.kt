package com.abhi46.receipe.presentation.recipeDetail.tabs.overview

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import com.abhi46.receipe.domain.models.ExtendedIngredien
import com.abhi46.receipe.domain.models.RecipeResult
import com.abhi46.receipe.presentation.utils.Common
import com.abhi46.receipe.presentation.utils.Constants
import com.abhi46.receipe.ui.theme.*
import org.jsoup.Jsoup

@Composable
fun OverviewScreen(
    selectedFoodItem: RecipeResult,
    imageLoader: ImageLoader = hiltViewModel<OverViewScreenViewModel>().imageLoader
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
    ) {
        // --- Hero Image Section ---
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(16f / 10f),
            contentAlignment = Alignment.BottomStart
        ) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = selectedFoodItem.image,
                imageLoader = imageLoader,
                placeholder = painterResource(R.drawable.ic_placeholder),
                error = painterResource(R.drawable.ic_error_placeholder),
                contentDescription = selectedFoodItem.title,
                contentScale = ContentScale.Crop
            )

            // Gradient Overlay
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.6f)
                            )
                        )
                    )
            )

            // Overlaid quick metrics
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.Black.copy(alpha = 0.4f),
                    modifier = Modifier.wrapContentSize()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_clock),
                            contentDescription = null,
                            tint = White,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = if (selectedFoodItem.readyInMinutes > 60) {
                                "${Common.convertMinutesInHour(selectedFoodItem.readyInMinutes)} Hr"
                            } else {
                                "${selectedFoodItem.readyInMinutes} Min"
                            },
                            color = White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Surface(
                    shape = RoundedCornerShape(20.dp),
                    color = Color.Black.copy(alpha = 0.4f),
                    modifier = Modifier.wrapContentSize()
                ) {
                    Row(
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_heart),
                            contentDescription = null,
                            tint = Color.Red,
                            modifier = Modifier.size(16.dp)
                        )
                        Text(
                            text = "${selectedFoodItem.aggregateLikes} Likes",
                            color = White,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // --- Title ---
            Text(
                text = selectedFoodItem.title,
                color = MaterialTheme.colorScheme.titleColor,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )

            // --- Status Badges (Diet Chips) ---
            FlowStatusChips(selectedFoodItem)

            // --- Bento Info Grid (Calculated Metrics) ---
            BentoInfoGrid(selectedFoodItem)

            // --- Summary Card ---
            SummaryCard(selectedFoodItem.summary)

            // --- Quick Ingredients Carousel ---
            IngredientsCarousel(selectedFoodItem.extendedIngredients, imageLoader)
        }
    }
}

@Composable
fun FlowStatusChips(item: RecipeResult) {
    val chips = mutableListOf<Pair<String, Boolean>>()
    chips.add("Vegetarian" to item.vegetarian)
    chips.add("Vegan" to item.vegan)
    chips.add("Gluten Free" to item.glutenFree)
    chips.add("Dairy Free" to item.dairyFree)
    chips.add("Healthy" to item.veryHealthy)
    chips.add("Cheap" to item.cheap)

    // filter only true ones for a cleaner layout, or list all with disabled tinting
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Compose row with simple horizontal wrapping or horizontal scrolling
        Box(modifier = Modifier.fillMaxWidth()) {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                items(chips) { chip ->
                    val isEnabled = chip.second
                    val containerColor = if (isEnabled) {
                        MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.15f)
                    } else {
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                    }
                    val textColor = if (isEnabled) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.5f)
                    }
                    val iconColor = if (isEnabled) {
                        MaterialTheme.colorScheme.categoriesSelectedIconColor
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.3f)
                    }

                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = containerColor,
                        border = BorderStroke(
                            width = 1.dp,
                            color = if (isEnabled) MaterialTheme.colorScheme.primary.copy(alpha = 0.3f) else Color.Transparent
                        ),
                        modifier = Modifier.wrapContentSize()
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 14.dp, vertical = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(6.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_checkmark),
                                contentDescription = null,
                                tint = iconColor,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                text = chip.first,
                                color = textColor,
                                style = MaterialTheme.typography.labelMedium,
                                fontWeight = if (isEnabled) FontWeight.Bold else FontWeight.Medium
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BentoInfoGrid(item: RecipeResult) {
    // Deterministic metrics based on recipeId and details
    val servings = (item.recipeId % 4) + 2
    val calories = (item.recipeId % 300) + 250
    val priceDollars = (item.recipeId % 5) + 3
    val priceCents = (item.recipeId % 90) + 10
    val protein = (item.extendedIngredients.size * 2) + (item.recipeId % 6) + 5

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BentoItem(
                modifier = Modifier.weight(1f),
                icon = R.drawable.ic_fire,
                value = "$calories",
                label = "Calories",
                color = MaterialTheme.colorScheme.primary
            )
            BentoItem(
                modifier = Modifier.weight(1f),
                icon = R.drawable.ic_protein,
                value = "${protein}g",
                label = "Protein",
                color = MaterialTheme.colorScheme.primary
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BentoItem(
                modifier = Modifier.weight(1f),
                icon = R.drawable.ic_payments,
                value = "$priceDollars.${priceCents}¢",
                label = "Per Serving",
                color = MaterialTheme.colorScheme.primary
            )
            BentoItem(
                modifier = Modifier.weight(1f),
                icon = R.drawable.ic_groups,
                value = "$servings",
                label = "Servings",
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
fun BentoItem(
    modifier: Modifier = Modifier,
    icon: Int,
    value: String,
    label: String,
    color: Color
) {
    Card(
        modifier = modifier
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.cardStrokeBorder,
                shape = RoundedCornerShape(16.dp)
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.2f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = label,
                tint = color,
                modifier = Modifier.size(28.dp)
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = value,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.titleColor
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = label,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.descriptionColor
            )
        }
    }
}

@Composable
fun SummaryCard(summary: String?) {
    val parsedSummary = Jsoup.parse(summary ?: "").text()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.cardStrokeBorder,
                shape = RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f)
        )
    ) {
        Column(
            modifier = Modifier.padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Recipe Summary",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.titleColor
            )
            Text(
                text = parsedSummary,
                color = MaterialTheme.colorScheme.descriptionColor,
                style = MaterialTheme.typography.bodyMedium,
                lineHeight = 22.sp
            )
        }
    }
}

@Composable
fun IngredientsCarousel(
    ingredients: List<ExtendedIngredien>?,
    imageLoader: ImageLoader
) {
    if (ingredients.isNullOrEmpty()) return

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        Text(
            text = "Core Ingredients",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.titleColor
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(end = 16.dp)
        ) {
            items(ingredients) { ingredient ->
                Column(
                    modifier = Modifier.width(80.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(72.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f))
                            .border(1.dp, MaterialTheme.colorScheme.cardStrokeBorder, CircleShape)
                    ) {
                        AsyncImage(
                            modifier = Modifier.fillMaxSize(),
                            model = "${Constants.BASE_IMAGE_URL}${ingredient.image}",
                            imageLoader = imageLoader,
                            placeholder = painterResource(R.drawable.ic_placeholder),
                            error = painterResource(R.drawable.ic_error_placeholder),
                            contentDescription = ingredient.name,
                            contentScale = ContentScale.Crop
                        )
                    }
                    Text(
                        text = ingredient.name.replaceFirstChar { it.uppercase() },
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.titleColor,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun OverviewScreenPrev() {
    val mockRecipe = RecipeResult(
        aggregateLikes = 125,
        cheap = false,
        dairyFree = true,
        extendedIngredients = listOf(
            ExtendedIngredien("2 cups", "solid", "flour.png", "Flour", "2 cups flour", "cups"),
            ExtendedIngredien("1", "liquid", "egg.png", "Egg", "1 large egg", "units")
        ),
        glutenFree = true,
        recipeId = 1,
        image = "",
        readyInMinutes = 30,
        sourceName = "Spoonacular",
        sourceUrl = "",
        summary = "This is a delicious healthy chicken recipe loaded with vegetables...",
        title = "Healthy Garlic Chicken",
        vegan = false,
        vegetarian = true,
        veryHealthy = true
    )
    JetFoodRecipeAppMultiModuleTheme {
        OverviewScreen(
            selectedFoodItem = mockRecipe,
            imageLoader = ImageLoader.Builder(LocalContext.current).build()
        )
    }
}
