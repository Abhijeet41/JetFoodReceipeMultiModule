package com.abhi41.core_network.service

import com.abhi41.core_network.dtos.food_joke.FoodRecipeDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipies(
        @QueryMap queries: Map<String, String>
    ): FoodRecipeDto
}