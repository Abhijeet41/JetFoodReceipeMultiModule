package com.abhi41.receipe.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import coil.ImageLoader
import com.abhi41.recipe.core_database.dao.RecipesDao
import com.abhi41.recipe.core_database.entity.FavoriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val dao: RecipesDao,
    val imageLoader: ImageLoader
): ViewModel() {
    val readFavoriteRecipes = dao.readFavoriteRecipes().asLiveData()

    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity){
        dao.deleteFavoriteRecipe(favoriteEntity = favoriteEntity)
    }

    suspend fun deleteAllFavoriteRecipes(){
        dao.deleteAllFavoriteRecipes()
    }

    suspend fun insertFavoriteRecipe(favoriteEntity: FavoriteEntity){
        dao.insertFavoriteRecipe(favoriteEntity = favoriteEntity)
    }
}


data class FavoriteState(
    val isContextual: Boolean = false,
    val multiSelection: Boolean = false,
    val selectedItem: Boolean = false,
    val actionModeTitle: String = ""
)