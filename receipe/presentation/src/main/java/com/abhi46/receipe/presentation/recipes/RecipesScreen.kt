package com.abhi46.receipe.presentation.recipes

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import com.abhi46.receipe.domain.models.RecipeResult
import com.abhi46.receipe.presentation.utils.Common
import com.abhi46.receipe.ui.theme.*
import org.jsoup.Jsoup

@Composable
fun RecipesScreen(
    modifier: Modifier = Modifier,
    onNavigationClick: (RecipeResult) -> Unit
) {
    val viewModel: RecipesViewModel = hiltViewModel()
    val recipesState by viewModel.recipesState

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        if (recipesState.isLoading) {
            AnimatedShimmer(modifier = Modifier.fillMaxSize())
        } else if (recipesState.error.isNotEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(
                    text = "No Recipes Found",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.titleColor
                )
            }
        } else if (recipesState.recipesItem.isNotEmpty()) {
            RecipeDesignContent(
                modifier = Modifier.fillMaxSize(),
                recipesItem = recipesState.recipesItem,
                onNavigationClick = { result ->
                    viewModel.logRecipeClickedEvent(
                        recipeId = result.recipeId.toString(),
                        recipeName = result.title,
                        screenName = "Recipes Screen",
                    )
                    viewModel.trackScreen()
                    onNavigationClick(result)
                }
            )
        }
    }
}

@Composable
fun RecipeDesignContent(
    modifier: Modifier,
    recipesItem: List<RecipeResult>,
    onNavigationClick: (RecipeResult) -> Unit,
    viewModel: RecipesViewModel = hiltViewModel()
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(
            items = recipesItem,
            key = { it.recipeId }
        ) { recipe ->
            RecipeItem(
                item = recipe,
                onNavigationClick = onNavigationClick,
                imageLoader = viewModel.imageLoader
            )
        }
    }
}

@Composable
fun RecipeItem(
    item: RecipeResult,
    onNavigationClick: (RecipeResult) -> Unit,
    imageLoader: ImageLoader
) {
    val cleanSummary = Jsoup.parse(item.summary).text()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onNavigationClick(item) }
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.cardStrokeBorder,
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
                    .height(220.dp)
            ) {
                AsyncImage(
                    modifier = Modifier.fillMaxSize(),
                    model = item.image,
                    imageLoader = imageLoader,
                    contentDescription = item.title,
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
                    val badgeColor = if (item.vegan) {
                        MaterialTheme.colorScheme.categoriesSelectedIconColor.copy(alpha = 0.9f)
                    } else {
                        MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.9f)
                    }
                    val badgeText = if (item.vegan) "Vegan" else "Non-vegan"
                    val badgeIcon = if (item.vegan) R.drawable.ic_leaf else R.drawable.ic_restaurant_24

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
                                tint = White,
                                modifier = Modifier.size(14.dp)
                            )
                            Text(
                                text = badgeText,
                                color = White,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                // Heart Icon overlay top-right
                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(16.dp)
                ) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        modifier = Modifier.size(40.dp)
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_favorite),
                                contentDescription = "Favorite Heart",
                                tint = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.size(20.dp)
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
                    text = item.title,
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
                            text = if (item.readyInMinutes > 60) {
                                "${Common.convertMinutesInHour(item.readyInMinutes)} Hr"
                            } else {
                                "${item.readyInMinutes} Min"
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
                            text = "${item.aggregateLikes}",
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
            animation = tween(durationMillis = 1200, easing = FastOutSlowInEasing)
        )
    )
    val brush = Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnimation.value, translateAnimation.value)
    )

    Column(
        modifier = modifier.padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        repeat(3) {
            ShimmerCardItem(brush = brush)
        }
    }
}

@Composable
fun ShimmerCardItem(brush: Brush) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(350.dp)
            .border(
                1.dp,
                MaterialTheme.colorScheme.cardStrokeBorder,
                shape = RoundedCornerShape(24.dp)
            ),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.1f)
        )
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .background(brush)
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp)
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth(0.7f)
                        .height(24.dp)
                        .background(brush, RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.height(12.dp))
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .background(brush, RoundedCornerShape(4.dp))
                )
                Spacer(modifier = Modifier.height(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Spacer(
                        modifier = Modifier
                            .width(80.dp)
                            .height(16.dp)
                            .background(brush, RoundedCornerShape(4.dp))
                    )
                    Spacer(
                        modifier = Modifier
                            .width(60.dp)
                            .height(16.dp)
                            .background(brush, RoundedCornerShape(4.dp))
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeItemPrev() {
    val mockRecipe = RecipeResult(
        aggregateLikes = 18,
        cheap = false,
        dairyFree = true,
        extendedIngredients = emptyList(),
        glutenFree = true,
        recipeId = 1,
        image = "",
        readyInMinutes = 45,
        sourceName = "Spoonacular",
        sourceUrl = "",
        summary = "This butternut squash frittata is baked beautifully...",
        title = "Butternut Squash Frittata",
        vegan = false,
        vegetarian = true,
        veryHealthy = true
    )
    JetFoodRecipeAppMultiModuleTheme {
        RecipeItem(
            item = mockRecipe,
            onNavigationClick = {},
            imageLoader = ImageLoader.Builder(LocalContext.current).build()
        )
    }
}