package com.abhi41.receipe.presentation.recipes

import com.abhi41.receipe.domain.models.Result

data class RecipesState(
    val recipesItem:List<Result> = emptyList(),
    val isLoading: Boolean = false
)
