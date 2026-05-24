package com.abhi46.receipe.presentation.joke

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhi46.receipe.domain.models.FoodJoke
import com.abhi46.receipe.domain.useCases.FoodJokeUsecase
import com.abhi46.receipe.domain.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FoodJokeViewModel @Inject constructor(
    private val foodJokeUsecase: FoodJokeUsecase
) : ViewModel(){

    private val _foodJokeState = mutableStateOf (FoodJokeState())
    val foodJokeState: State<FoodJokeState> = _foodJokeState


    init {
        getFoodJoke()
    }

    fun getFoodJoke(){
        viewModelScope.launch(Dispatchers.IO){
            foodJokeUsecase().onEach { result->
                when(result){
                    is Resource.Loading -> {
                        withContext(Dispatchers.Main) {
                            _foodJokeState.value = _foodJokeState.value.copy(
                                foodJoke = result.data ?: emptyList(),
                                isLoading = true
                            )
                        }
                    }
                    is Resource.Success-> {
                        _foodJokeState.value = _foodJokeState.value.copy(
                            foodJoke = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                    is Resource.Error-> {
                        _foodJokeState.value = _foodJokeState.value.copy(
                            foodJoke = result.data ?: emptyList(),
                            isLoading = false
                        )
                    }
                }
            }.launchIn(this)
        }
    }

}

data class FoodJokeState(
    val foodJoke:List<FoodJoke> = emptyList(),
    val isLoading:Boolean = false
)