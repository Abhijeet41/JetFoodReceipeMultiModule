package com.abhi41.receipe.domain.models

data class Result(
    val aggregateLikes: Int,
    val cheap: Boolean,
    val dairyFree: Boolean,
    val extendedIngredients:List<ExtendedIngredien>,
    val glutenFree: Boolean,
    val recipeId: Int,
    val image: String,
    val readyInMinutes: Int,
    val sourceName: String?,
    val sourceUrl: String,
    val summary: String,
    val title: String,
    val vegan: Boolean,
    val vegetarian: Boolean,
    val veryHealthy: Boolean,
)


data class ExtendedIngredien(
    val amount: Double,
    val consistency: String,
    val image: String?,
    val name: String,
    val original: String,
    val unit: String
)

data class FoodRecipe(
    val results: List<Result>
)