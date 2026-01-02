package com.abhi41.receipe.presentation.recipes

import com.abhi41.receipe.domain.models.RecipeResult

data class RecipesState(
    val recipesItem:List<RecipeResult> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
