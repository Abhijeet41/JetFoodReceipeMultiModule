package com.abhi46.receipe.presentation.recipes

import com.abhi46.receipe.domain.models.RecipeResult

data class RecipesState(
    val recipesItem:List<RecipeResult> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)
