package com.abhi46.receipe.data.di

import android.content.Context
import android.os.Build
import androidx.annotation.RequiresExtension
import com.abhi46.core_network.service.FoodRecipesApi
import com.abhi46.receipe.data.repository.RecipesRepositoryImpl
import com.abhi46.receipe.domain.repository.RecipesRepository
import com.abhi46.recipe.core_database.dao.FoodJokeDao
import com.abhi46.recipe.core_database.dao.RecipesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@InstallIn(SingletonComponent::class)
@Module
object RecipeDataModule {

    @Provides
    fun provideRecipeRepository(
        apiService: FoodRecipesApi,
        recipesDao: RecipesDao,
        foodJokeDao: FoodJokeDao,
        @ApplicationContext context: Context
    ): RecipesRepository {
        return RecipesRepositoryImpl(apiService, recipesDao, foodJokeDao,context)
    }


}




