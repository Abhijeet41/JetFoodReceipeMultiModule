package com.abhi46.receipe.presentation.joke

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.abhi46.receipe.ui.theme.JetFoodRecipeAppMultiModuleTheme
import com.abhi46.receipe.ui.theme.titleColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JokeTopBar() {
    TopAppBar(
        title = {
            Text(
                text = "Food Joke",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.titleColor
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.9f)
        )
    )
}

@Preview
@Composable
fun JokeTopBarPreview() {
    JetFoodRecipeAppMultiModuleTheme {
        JokeTopBar()
    }
}