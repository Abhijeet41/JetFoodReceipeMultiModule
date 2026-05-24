package com.abhi46.receipe.data.mappers

import com.abhi46.core_network.dtos.food_joke.FoodJokeDto
import com.abhi46.receipe.domain.models.FoodJoke
import com.abhi46.receipe.domain.models.RecipeResult
import com.abhi46.recipe.core_database.entity.FavoriteEntity
import com.abhi46.recipe.core_database.entity.FoodJokeEntity

// Mapper function to convert from the Domain model TO the Database entity
fun RecipeResult.toFavoriteEntity(): FavoriteEntity {
    return FavoriteEntity(
        recipeId = this.recipeId,
        aggregateLikes = this.aggregateLikes,
        image = this.image,
        readyInMinutes = this.readyInMinutes,
        sourceUrl = this.sourceUrl,
        summary = this.summary,
        title = this.title,
        vegan = this.vegan,
        vegetarian = this.vegetarian,
        veryHealthy = this.veryHealthy,
        cheap = this.cheap,
        dairyFree = this.dairyFree,
        extendedIngredients = this.extendedIngredients.toIngredient(),
        glutenFree = this.glutenFree,
        sourceName = this.sourceName
    )
}

//Mapper function to convert from the Database entity BACK TO the Domain model
fun FavoriteEntity.toRecipeResult(): RecipeResult {
    return RecipeResult(
        recipeId = this.recipeId,
        aggregateLikes = this.aggregateLikes,
        image = this.image,
        readyInMinutes = this.readyInMinutes,
        sourceUrl = this.sourceUrl,
        summary = this.summary,
        title = this.title,
        vegan = this.vegan,
        vegetarian = this.vegetarian,
        veryHealthy = this.veryHealthy,
        cheap = this.cheap,
        dairyFree = this.dairyFree,
        extendedIngredients = this.extendedIngredients.toLocalIngredient(), // Assuming you have a reverse mapper for ingredients
        glutenFree = this.glutenFree,
        sourceName = this.sourceName
    )
}

fun FoodJokeDto.toFoodJokeEntity(): FoodJokeEntity{
    return FoodJokeEntity(
        text = text
    )
}

fun FoodJokeEntity.toFoodJoke(): FoodJoke{
    return FoodJoke(
        text = text
    )
}



