package com.abhi41.receipe.presentation.search

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.wear.compose.material.ContentAlpha
import com.abhi41.receipe.domain.models.RecipeResult
import com.abhi41.receipe.presentation.recipes.AnimatedShimmer
import com.abhi41.receipe.presentation.recipes.RecipeDesignContent
import com.abhi41.receipe.ui.theme.TOP_APP_BAR_HEIGHT
import com.abhi41.receipe.ui.theme.topAppBarBackgroundColor
import com.abhi41.receipe.ui.theme.topAppBarContentColor

@Composable
fun SearchScreen(
    searchViewModel: SearchViewModel = hiltViewModel(),
    onNavigationClick: (RecipeResult) -> Unit,
    onClosedClicked: () -> Unit
) {
    val state = searchViewModel.searchState.value
    val searchRecipes = state.result
    val searchQuery by searchViewModel.searchQuery

    Scaffold (
        topBar = {
            SearchTopBar(
                text = searchQuery,
                onTextChange = {
                    searchViewModel.updateSearchQuery(query = it)
                    searchViewModel.cleareSearchList()
                },
                onSearchedClicked = {searchQuery ->
                    searchViewModel.getSearchRecipes(searchQuery = searchQuery)
                },
                onClosedClicked = {
                    onClosedClicked()
                }
            )
        }
    ){ innerPadding ->
        val modifier = Modifier
            .padding(innerPadding)

        if (!searchViewModel.searchState.value.isLoading){
            RecipeDesignContent(
                modifier =modifier.fillMaxSize(),
                recipesItem = searchRecipes,
                onNavigationClick = {result ->
                    onNavigationClick(result)
                }
            )
        }else{
            AnimatedShimmer(modifier)
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
    SearchWidget(
        text = text,
        onTextChange = onTextChange,
        onSearchedClicked = {
            onSearchedClicked(it)
        },
        onClosedClicked = onClosedClicked
    )
}

@Composable
fun SearchWidget(
    text: String,
    onTextChange: (String) -> Unit,
    onSearchedClicked: (String) -> Unit,
    onClosedClicked: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    Surface(
        modifier = Modifier
            .padding(top = 40.dp, start = 10.dp, end = 10.dp)
            .height(TOP_APP_BAR_HEIGHT),
        shadowElevation = 8.dp,
        color = MaterialTheme.colorScheme.topAppBarBackgroundColor
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = text,
            onValueChange = { onTextChange(it) },
            placeholder = {
                Text(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    text = "Search here...",
                    color = Color.White
                )
            },
            textStyle = TextStyle(
                color = MaterialTheme.colorScheme.topAppBarContentColor
            ),
            singleLine = true,
            leadingIcon = {
                IconButton(
                    modifier = Modifier.alpha(ContentAlpha.medium),
                    onClick = {

                    }) {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon",
                        tint = MaterialTheme.colorScheme.topAppBarContentColor
                    )
                }
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
                        contentDescription = "Icon Close",
                        tint = MaterialTheme.colorScheme.topAppBarContentColor
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearchedClicked(text)
                    keyboardController?.hide()
                }
            ),
           colors = TextFieldDefaults.colors(
               focusedContainerColor = Color.Transparent,
               unfocusedContainerColor = Color.Transparent,
               cursorColor = MaterialTheme.colorScheme.topAppBarContentColor,
               focusedIndicatorColor = Color.Transparent, // To hide the underline
               unfocusedIndicatorColor = Color.Transparent // To hide the underline
           )
        )
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun SearchTopBarPreview() {
    SearchTopBar(
        text = "Chicken", // Example text to see how it looks filled
        onTextChange = {},
        onSearchedClicked = {},
        onClosedClicked = {}
    )
}