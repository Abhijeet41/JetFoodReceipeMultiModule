package com.abhi41.receipe.data.di

import android.os.Build
import androidx.annotation.RequiresExtension
import com.abhi41.core_network.service.FoodRecipesApi
import com.abhi41.receipe.data.repository.RecipesRepositoryImpl
import com.abhi41.receipe.domain.repository.RecipesRepository
import com.abhi41.recipe.core_database.dao.RecipesDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@InstallIn(SingletonComponent::class)
@Module
object RecipeDataModule {

    @Provides
    fun provideRecipeRepository(apiService: FoodRecipesApi, recipesDao: RecipesDao): RecipesRepository {
        return RecipesRepositoryImpl(apiService, recipesDao)
    }
}




