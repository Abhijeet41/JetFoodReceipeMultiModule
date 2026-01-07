package com.abhi41.receipe.data.mappers

import com.abhi41.receipe.domain.models.RecipeResult
import com.abhi41.recipe.core_database.entity.FavoriteEntity

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

