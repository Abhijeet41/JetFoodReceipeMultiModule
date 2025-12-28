package com.abhi41.receipe.presentation.recipes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.abhi41.receipe.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipesScreen(
    modifier: Modifier
) {
    val viewModel: RecipesViewModel = hiltViewModel()

    /* LaunchedEffect(key1 = Unit){
         viewModel.getRecipes()
     }*/

    RecipeDesignContent()

}

@Composable
fun RecipeDesignContent(modifier: Modifier = Modifier) {

}

@Composable
fun RecipeItem(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(text = "Recipe Title")
            Text(text = "This is a sample recipe item.")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeItemPrev() {
    RecipeItem()
}

// Add a new preview for the whole screen
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun RecipesScreenPreview() {
    // This will now render correctly because RecipeDesignContent is no longer empty
    RecipeDesignContent()
}