package com.abhi46.receipe.presentation.recipeDetail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abhi46.receipe.domain.models.RecipeResult
import com.abhi46.receipe.ui.theme.JetFoodRecipeAppMultiModuleTheme
import com.abhi46.receipe.ui.theme.titleColor
import com.abhi46.recipe.core_database.entity.FavoriteEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailedScreenAppBar(
    onBackArrowClicked: () -> Unit,
    onFavoriteClicked: (isRecipeSaved: Boolean) -> Unit,
    selectedRecipe: RecipeResult?,
    favoritesRecipes: List<FavoriteEntity>,
) {
    val isRecipeSaved = remember(favoritesRecipes) {
        favoritesRecipes.any { it.recipeId == selectedRecipe?.recipeId }
    }

    TopAppBar(
        title = {
            Text(
                text = "Details",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.titleColor
            )
        },
        navigationIcon = {
            IconButton(
                onClick = onBackArrowClicked,
                modifier = Modifier
                    .padding(start = 8.dp)
                    .size(40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back),
                    contentDescription = "Back",
                    tint = MaterialTheme.colorScheme.titleColor,
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        actions = {
            IconButton(
                onClick = { onFavoriteClicked(isRecipeSaved) },
                modifier = Modifier
                    .padding(end = 16.dp)
                    .size(40.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
                        shape = CircleShape
                    )
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_favorite),
                    contentDescription = "Favorite",
                    tint = if (isRecipeSaved) {
                        MaterialTheme.colorScheme.secondary
                    } else {
                        MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)
                    },
                    modifier = Modifier.size(20.dp)
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun DetailedScreenAppBarPrev() {
    JetFoodRecipeAppMultiModuleTheme {
        DetailedScreenAppBar(
            onBackArrowClicked = {},
            onFavoriteClicked = {},
            selectedRecipe = null,
            favoritesRecipes = emptyList(),
        )
    }
}
