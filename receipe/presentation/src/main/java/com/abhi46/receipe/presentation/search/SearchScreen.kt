package com.abhi46.receipe.presentation.search

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhi46.receipe.domain.models.RecipeResult
import com.abhi46.receipe.presentation.recipes.AnimatedShimmer
import com.abhi46.receipe.presentation.recipes.RecipeDesignContent
import com.abhi46.receipe.ui.theme.*

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    onNavigationClick: (RecipeResult) -> Unit,
    onClosedClicked: () -> Unit
) {
    val state = searchViewModel.searchState.value
    val searchRecipes = state.result
    val searchQuery by searchViewModel.searchQuery

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            SearchTopBar(
                text = searchQuery,
                onTextChange = {
                    searchViewModel.updateSearchQuery(query = it)
                    searchViewModel.cleareSearchList()
                },
                onSearchedClicked = { query ->
                    searchViewModel.getSearchRecipes(searchQuery = query)
                },
                onClosedClicked = onClosedClicked
            )
        }
    ) { innerPadding ->
        val modifier = Modifier
            .padding(innerPadding)
            .fillMaxSize()

        if (state.isLoading) {
            AnimatedShimmer(modifier)
        } else if (searchRecipes.isEmpty() && searchQuery.isNotEmpty() && !state.isLoading) {
            Box(modifier = modifier, contentAlignment = Alignment.Center) {
                Text(
                    text = "No results found for \"$searchQuery\"",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.descriptionColor
                )
            }
        } else {
            RecipeDesignContent(
                modifier = modifier,
                recipesItem = searchRecipes,
                onNavigationClick = onNavigationClick
            )
        }
    }
}

@Composable
fun SearchTopBar(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchedClicked: (String) -> Unit,
    onClosedClicked: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        color = Color.Transparent
    ) {
        SearchWidget(
            text = text,
            onTextChange = onTextChange,
            onSearchedClicked = onSearchedClicked,
            onClosedClicked = onClosedClicked
        )
    }
}

@Composable
fun SearchWidget(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchedClicked: (String) -> Unit,
    onClosedClicked: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    TextField(
        value = text,
        onValueChange = onTextChange,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(
                color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                shape = RoundedCornerShape(28.dp)
            ),
        placeholder = {
            Text(
                text = "Search recipes...",
                color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                style = MaterialTheme.typography.bodyMedium
            )
        },
        textStyle = TextStyle(
            color = MaterialTheme.colorScheme.titleColor,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        ),
        singleLine = true,
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Search,
                contentDescription = "Search Icon",
                tint = MaterialTheme.colorScheme.primary
            )
        },
        trailingIcon = {
            IconButton(onClick = {
                if (text.isNotEmpty()) {
                    onTextChange("")
                } else {
                    onClosedClicked()
                }
            }) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close Icon",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                if (text.isNotBlank()) {
                    onSearchedClicked(text)
                }
                keyboardController?.hide()
            }
        ),
        shape = RoundedCornerShape(28.dp),
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.Transparent,
            unfocusedContainerColor = Color.Transparent,
            disabledContainerColor = Color.Transparent,
            cursorColor = MaterialTheme.colorScheme.primary,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        )
    )
}

@Preview(showBackground = true)
@Composable
fun SearchTopBarPreview() {
    JetFoodRecipeAppMultiModuleTheme {
        SearchTopBar(
            text = "Chicken",
            onTextChange = {},
            onSearchedClicked = {},
            onClosedClicked = {}
        )
    }
}