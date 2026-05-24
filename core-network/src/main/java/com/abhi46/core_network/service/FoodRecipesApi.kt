package com.abhi46.core_network.service

import com.abhi46.core_network.dtos.food_joke.FoodJokeDto
import com.abhi46.core_network.dtos.food_joke.FoodRecipeDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface FoodRecipesApi {

    @GET("/recipes/complexSearch")
    suspend fun getRecipies(
        @QueryMap queries: Map<String, String>
    ): FoodRecipeDto

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(
        @QueryMap searchQuery: Map<String, String>
    ): Response<FoodRecipeDto>

    @GET("/food/jokes/random")
    suspend fun getFoodJoke(
        @Query("apiKey") apiKey:String
    ): FoodJokeDto

}