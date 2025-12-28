package com.abhi41.receipe.presentation.recipes

import android.util.Log
import androidx.lifecycle.ViewModel
import com.abhi41.receipe.domain.useCases.GetAllRecipesUseCase
import com.abhi41.receipe.domain.utils.Resource
import com.abhi41.receipe.presentation.utils.Constants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val getAllRecipesUseCase: GetAllRecipesUseCase
) : ViewModel() {

    private val _recipesState = mutableStateOf(RecipesState())
    val recipesState: State<RecipesState> = _recipesState



    fun getRecipes() {
       viewModelScope.launch (Dispatchers.IO){
           getAllRecipesUseCase(
               queries = applyQuries("", "")
           ).onEach { result ->
               when (result) {
                   is Resource.Success -> {
                       _recipesState.value = recipesState.value.copy(
                           recipesItem = result.data ?: emptyList(),
                           isLoading = false
                       )
                   }

                   is Resource.Error -> {
                       _recipesState.value = recipesState.value.copy(
                           recipesItem = result.data ?: emptyList(),
                           isLoading = false
                       )
                   }

                   is Resource.Loading -> {
                       withContext(Dispatchers.Main) {
                           _recipesState.value = recipesState.value.copy(
                               recipesItem = result.data ?: emptyList(),
                               isLoading = true
                           )
                       }
                   }
               }
           }.launchIn(this)
       }
    }

    fun applyQuries(
        mealType: String,
        dietType: String,
    ): HashMap<String, String> {
        val quries: HashMap<String, String> = HashMap()

        quries[Constants.QUERY_NUMBER] = "50"
        quries[Constants.QUERY_API_KEY] = Constants.API_KEY
        quries[Constants.QUERY_TYPE] = "main course"        // "main course"
        quries[Constants.QUERY_DIET] = "gluten free"                //"gluten free"
        quries[Constants.QUERY_ADD_RECIPE_INFO] = "true"
        quries[Constants.QUERY_FILL_INGREDIENTS] = "true"

        Log.d("VM_mealType", mealType)
        Log.d("VM_dietType", dietType)

        return quries
    }
}