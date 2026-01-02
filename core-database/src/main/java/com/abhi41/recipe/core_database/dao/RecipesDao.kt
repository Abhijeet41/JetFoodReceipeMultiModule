package com.abhi41.recipe.core_database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhi41.recipe.core_database.entity.ResultEntity

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: List<ResultEntity>)

    @Query("SELECT * FROM recipes_table ORDER BY recipeId ASC")
    fun readRecipes(): List<ResultEntity>

    @Query("DELETE FROM recipes_table")
    suspend fun deleteAllRecipes()


}