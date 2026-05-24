package com.abhi46.receipe.domain.repository

import com.abhi46.receipe.domain.models.FoodJoke
import com.abhi46.receipe.domain.models.RecipeResult
import com.abhi46.receipe.domain.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RecipesRepository {
    fun getRecipes(queries: Map<String, String>): Flow<Resource<List<RecipeResult>>>

     fun getSearchRecipes(queries: Map<String, String>):Flow<Resource<List<RecipeResult>>>

    fun getFoodJokes(): Flow<Resource<List<FoodJoke>>>
}