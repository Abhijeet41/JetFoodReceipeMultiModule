package com.abhi41.receipe.domain.useCases

import com.abhi41.receipe.domain.models.FoodJoke
import com.abhi41.receipe.domain.repository.RecipesRepository
import com.abhi41.receipe.domain.utils.Resource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FoodJokeUsecase @Inject constructor(
    private val  repository: RecipesRepository
) {
    operator fun invoke(): Flow<Resource<List<FoodJoke>>>{
        return repository.getFoodJokes()
    }
}