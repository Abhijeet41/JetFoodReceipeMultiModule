package com.abhi46.recipe.core_database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.abhi46.recipe.core_database.entity.FavoriteEntity
import com.abhi46.recipe.core_database.entity.ResultEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipes(recipesEntity: List<ResultEntity>)

    @Query("SELECT * FROM recipes_table ORDER BY recipeId ASC")
    fun readRecipes(): List<ResultEntity>

    @Query("DELETE FROM recipes_table")
    suspend fun deleteAllRecipes()

    //----------------------Favorite Table ----------------------------
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite_recipes_table ORDER BY id ASC")
    fun readFavoriteRecipes(): Flow<List<FavoriteEntity>>

    @Delete
    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM favorite_recipes_table WHERE recipeId = :recipeId")
    suspend fun deleteFavoriteRecipeById(recipeId: Int)

    @Query("DELETE FROM favorite_recipes_table")
    suspend fun deleteAllFavoriteRecipes()

}