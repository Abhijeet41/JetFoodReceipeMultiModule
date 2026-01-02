package com.abhi41.receipe.domain.repository

import com.abhi41.receipe.domain.models.RecipeResult
import com.abhi41.receipe.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {
    fun getRecipes(queries: Map<String, String>): Flow<Resource<List<RecipeResult>>>

    // fun getSearchRecipes(queries: Map<String, String>): kotlin.Result<Result>
}