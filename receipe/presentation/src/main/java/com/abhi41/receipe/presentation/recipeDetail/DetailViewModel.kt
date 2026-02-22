package com.abhi41.receipe.presentation.recipeDetail

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import com.abhi41.recipe.core_database.dao.RecipesDao
import com.abhi41.recipe.core_database.entity.FavoriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val recipesDao: RecipesDao
) : ViewModel() {
    //read flow recipes of favorite as a livedata
    val readFavoriteRecipes = recipesDao.readFavoriteRecipes().asLiveData()


    fun insertFavoriteRecipes(recipes: FavoriteEntity) {
        viewModelScope.launch(Dispatchers.IO) {
            recipesDao.insertFavoriteRecipe(favoriteEntity = recipes)
        }
    }

    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity) {
        return recipesDao.deleteFavoriteRecipe(favoriteEntity = favoriteEntity)
    }
    suspend fun deleteFavoriteRecipeById(recipeId: Int) {
        recipesDao.deleteFavoriteRecipeById(recipeId)
    }

    suspend fun deleteAllFavoriteRecipes() {
        return recipesDao.deleteAllFavoriteRecipes()
    }
}