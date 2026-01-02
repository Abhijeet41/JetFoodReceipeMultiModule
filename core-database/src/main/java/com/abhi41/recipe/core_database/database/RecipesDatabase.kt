package com.abhi41.recipe.core_database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.abhi41.recipe.core_database.converters.RecipesTypeConverter
import com.abhi41.recipe.core_database.dao.RecipesDao
import com.abhi41.recipe.core_database.entity.ResultEntity

@Database(
    entities = [ResultEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase : RoomDatabase() {

    abstract fun recipeDao(): RecipesDao

}