package com.abhi46.recipe.core_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi46.recipe.core_database.utils.Constants

@Entity(tableName = Constants.FAVORITE_RECIPES_TABLE)
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    val recipeId: Int,
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
