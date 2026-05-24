package com.abhi46.receipe.presentation.recipeDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.abhi46.receipe.domain.utils.DispatcherProvider
import com.abhi46.recipe.core_database.dao.RecipesDao
import com.abhi46.recipe.core_database.entity.FavoriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    val recipesDao: RecipesDao,
    private val dispatcherProvider: DispatcherProvider
) : ViewModel() {
    //read flow recipes of favorite as a livedata
    val readFavoriteRecipes = recipesDao.readFavoriteRecipes().asLiveData()


    fun insertFavoriteRecipes(recipes: FavoriteEntity) {
        viewModelScope.launch(dispatcherProvider.io) {
            recipesDao.insertFavoriteRecipe(favoriteEntity = recipes)
        }
    }
    fun deleteFavoriteRecipeById(recipeId: Int) {
        viewModelScope.launch(dispatcherProvider.io) {
            recipesDao.deleteFavoriteRecipeById(recipeId)
        }
    }

}