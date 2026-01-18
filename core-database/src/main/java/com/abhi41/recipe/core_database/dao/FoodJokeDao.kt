package com.abhi41.recipe.core_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhi41.recipe.core_database.entity.FoodJokeEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodJokeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity)

    @Query("SELECT * FROM food_joke_table ORDER BY id ASC")
    fun readFoodJoke():List<FoodJokeEntity>

    @Query("DELETE FROM food_joke_table")
    suspend fun deleteAllFoodJoke()

}