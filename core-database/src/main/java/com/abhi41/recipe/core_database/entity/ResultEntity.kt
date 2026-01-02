package com.abhi41.recipe.core_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi41.recipe.core_database.utils.Constants

@Entity(tableName = Constants.RECIPES_TABLE)
data class ResultEntity(
    @PrimaryKey val recipeId: Int,
    val aggregateLikes: Int,
    val cheap: Boolean,
    val dairyFree: Boolean,
    val extendedIngredients: List<ExtendedIngredient>,
    val glutenFree: Boolean,
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
data class ExtendedIngredient(
    val amount: Double,
    val consistency: String,
    val image: String?,
    val name: String,
    val original: String,
    val unit: String
)