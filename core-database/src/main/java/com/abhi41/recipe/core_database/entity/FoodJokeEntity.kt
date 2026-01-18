package com.abhi41.recipe.core_database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.abhi41.recipe.core_database.utils.Constants

@Entity(tableName = Constants.FOOD_JOKE_TABLE)
data class FoodJokeEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int? = null,
    val text: String?
)
