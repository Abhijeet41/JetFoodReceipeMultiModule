package com.abhi41.receipe.data.mappers

import com.abhi41.core_network.dtos.receipe.ExtendedIngredientDto
import com.abhi41.core_network.dtos.receipe.ResultDto
import com.abhi41.receipe.domain.models.ExtendedIngredien
import com.abhi41.receipe.domain.models.RecipeResult
import com.abhi41.recipe.core_database.entity.ExtendedIngredient
import com.abhi41.recipe.core_database.entity.ResultEntity

fun List<ResultDto>.toDomainRecipes(): List<RecipeResult> {
   return map {
       RecipeResult(
           aggregateLikes = it.aggregateLikes,
           cheap = it.cheap,
           dairyFree = it.dairyFree,
           extendedIngredients = it.extendedIngredients.toDomainExtendedIngredient(),
           glutenFree = it.glutenFree,
           recipeId = it.recipeId, // Mapping 'id' from DTO to 'recipeId' in domain model
           image = it.image,
           readyInMinutes = it.readyInMinutes,
           sourceName = it.sourceName,
           sourceUrl = it.sourceUrl,
           summary = it.summary,
           title = it.title,
           vegan = it.vegan,
           vegetarian = it.vegetarian,
           veryHealthy = it.veryHealthy
       )
   }
}
// Add this mapper function to the same file (Mappers.kt)
fun List<ExtendedIngredientDto>.toDomainExtendedIngredient(): List<ExtendedIngredien> {
    return map {
        ExtendedIngredien(
            amount = it.amount,
            consistency = it.consistency,
            image = it.image,
            name = it.name,
            original = it.original,
            unit = it.unit
        )
    }
}


fun List<ResultEntity>.toReadLocalRecipes(): List<RecipeResult> {
    return map {
        RecipeResult(
            aggregateLikes = it.aggregateLikes,
            cheap = it.cheap,
            dairyFree = it.dairyFree,
            extendedIngredients = it.extendedIngredients.toLocalIngredient(),
            glutenFree = it.glutenFree,
            recipeId = it.recipeId, // Mapping 'id' from DTO to 'recipeId' in domain model
            image = it.image,
            readyInMinutes = it.readyInMinutes,
            sourceName = it.sourceName,
            sourceUrl = it.sourceUrl,
            summary = it.summary,
            title = it.title,
            vegan = it.vegan,
            vegetarian = it.vegetarian,
            veryHealthy = it.veryHealthy
        )
    }
}
// Add this mapper function to the same file (Mappers.kt)
fun List<ExtendedIngredient>.toLocalIngredient(): List<ExtendedIngredien> {
    return map {
        ExtendedIngredien(
            amount = it.amount,
            consistency = it.consistency,
            image = it.image,
            name = it.name,
            original = it.original,
            unit = it.unit
        )
    }
}

fun List<RecipeResult>.toInsertRecipes(): List<ResultEntity> {
    return map {
        ResultEntity(
            aggregateLikes = it.aggregateLikes,
            cheap = it.cheap,
            dairyFree = it.dairyFree,
            extendedIngredients = it.extendedIngredients.toIngredient(),
            glutenFree = it.glutenFree,
            recipeId = it.recipeId, // Mapping 'id' from DTO to 'recipeId' in domain model
            image = it.image,
            readyInMinutes = it.readyInMinutes,
            sourceName = it.sourceName,
            sourceUrl = it.sourceUrl,
            summary = it.summary,
            title = it.title,
            vegan = it.vegan,
            vegetarian = it.vegetarian,
            veryHealthy = it.veryHealthy
        )
    }
}
// Add this mapper function to the same file (Mappers.kt)
fun List<ExtendedIngredien>.toIngredient(): List<ExtendedIngredient> {
    return map {
        ExtendedIngredient(
            amount = it.amount,
            consistency = it.consistency,
            image = it.image,
            name = it.name,
            original = it.original,
            unit = it.unit
        )
    }
}


