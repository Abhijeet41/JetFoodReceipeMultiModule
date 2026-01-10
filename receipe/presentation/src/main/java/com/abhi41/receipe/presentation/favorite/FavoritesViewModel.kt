package com.abhi41.receipe.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.abhi41.recipe.core_database.dao.RecipesDao
import com.abhi41.recipe.core_database.entity.FavoriteEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val dao: RecipesDao
): ViewModel() {
    val readFavoriteRecipes = dao.readFavoriteRecipes().asLiveData()

    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity){
        dao.deleteFavoriteRecipe(favoriteEntity = favoriteEntity)
    }

    suspend fun deleteAllFavoriteRecipes(){
        dao.deleteAllFavoriteRecipes()
    }
}


data class FavoriteState(
    val isContextual: Boolean = false,
    val multiSelection: Boolean = false,
    val selectedItem: Boolean = false,
    val actionModeTitle: String = ""
)